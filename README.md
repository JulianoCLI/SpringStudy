# Desafio Técnico: Sistema de Gerenciamento de Biblioteca Executiva (Spring Boot API)

## Contexto
Você foi contratado para desenvolver o motor principal de um sistema de biblioteca corporativa. O objetivo é gerenciar o acervo de livros, os usuários cadastrados e o controle de empréstimos ativos. 

Diferente de uma implementação em memória simples, este projeto utiliza o ecossistema moderno do **Java com Spring Boot**, persistindo os dados em banco de dados relacional via **Spring Data JPA** e simplificando o código com **Lombok**.

Seu papel é finalizar este projeto aplicando as regras de negócio corretamente, garantindo a integridade dos dados e expondo as funcionalidades através de uma **API REST** funcional.

---

## Requisitos de Negócio e Regras

### 1. Modelagem de Dados (JPA & Lombok)
O projeto já conta com a estrutura inicial das entidades sob o pacote `com.julianocli.springstudy`. Você deve utilizá-las e garantir o seguinte comportamento:

*   **`LivrosModel`** (Tabela `tb_livros`):
    *   Campos existentes: `id` (gerado automaticamente pelo banco como `IDENTITY`), `titulo` e a relação `@ManyToOne` com `UserModel` (mapeada sob a coluna `user_id`).
    *   **Regra de Negócio**: Um livro está *disponível* para empréstimo se, e somente se, o atributo `user` for `null`. Se o atributo `user` estiver preenchido, significa que o livro está emprestado para aquele usuário.
    *   **Regra de Equals/HashCode**: Implemente a comparação de igualdade utilizando o atributo `id` através da anotação `@EqualsAndHashCode(onlyExplicitlyIncluded = true)` do Lombok sobre o campo `id` para garantir que o comportamento seja consistente com o ciclo de vida do JPA.

*   **`UserModel`** (Tabela `tb_cadastro`):
    *   Campos existentes: `id` (gerado automaticamente como `IDENTITY`), `name`, `email`, `idade` e a lista de livros emprestados mapeada bidirecionalmente por `@OneToMany(mappedBy = "user")`.
    *   **Regra de Equals/HashCode**: Assim como no Livro, implemente o `equals()` e `hashCode()` baseados no `id` de forma explícita e segura usando Lombok.

---

### 2. Camada de Persistência (Spring Data JPA Repositories)
Você deve criar as interfaces de repositório necessárias para persistir e buscar os dados no banco:
*   `LivrosRepository` herdando de `JpaRepository<LivrosModel, Long>`.
*   `UserRepository` herdando de `JpaRepository<UserModel, Long>`.

---

### 3. Camada de Serviço: O Gerenciador da Biblioteca (`BibliotecaService`)
Esta classe centralizará a lógica de negócios e fará a ponte entre a persistência de dados e a camada HTTP (Controller). Crie os seguintes fluxos:

#### **A. Cadastro de Livros e Usuários**
*   Utilize os métodos dos repositórios para salvar novos registros no banco de dados. Como o ID é gerado automaticamente, garanta validações básicas na criação (como impedir e-mails duplicados ou nomes vazios, se desejar).

#### **B. Empréstimo de Livro**
*   **Assinatura recomendada**: `public void realizarEmprestimo(Long idUsuario, Long idLivro)`
*   **Validações Obrigatórias**:
    1.  O **livro** deve existir no banco de dados. Caso contrário, retorne um erro apropriado (ex: `404 Not Found`).
    2.  O **usuário** deve existir no banco de dados. Caso contrário, retorne erro.
    3.  O livro deve estar **disponível** (ou seja, `book.getUser() == null`). Se o livro já estiver associado a algum usuário, impeça a operação e retorne um erro de regra de negócio (ex: `400 Bad Request` com uma mensagem descritiva).
    4.  O usuário pode ter **no máximo 3 livros** emprestados ao mesmo tempo. Valide o tamanho da lista `livros` no `UserModel` (se `user.getLivros().size() >= 3`, impeça o novo empréstimo).
*   **Ação**: Associe o livro ao usuário (`livro.setUser(usuario)`) e persista a alteração no banco de dados.

#### **C. Devolução de Livro**
*   **Assinatura recomendada**: `public void realizarDevolucao(Long idUsuario, Long idLivro)`
*   **Validações Obrigatórias**:
    1.  Tanto o livro quanto o usuário devem existir.
    2.  O livro deve estar atualmente associado ao usuário informado (ou seja, `livro.getUser() != null && livro.getUser().getId() == idUsuario`). Caso contrário, impeça a operação com um erro de negócio.
*   **Ação**: Remova o vínculo do livro com o usuário (`livro.setUser(null)`) e salve as alterações no banco de dados.

---

### 4. Camada de Controle (API REST - Controllers)
Em vez de usar uma classe com método `main` e coleções na memória, você deve expor as funcionalidades da aplicação através de endpoints HTTP estruturados:

*   `POST /usuarios` - Cria um novo usuário (`UserModel`).
*   `POST /livros` - Cria um novo livro (`LivrosModel`) no acervo.
*   `POST /biblioteca/emprestimo/{idUsuario}/{idLivro}` - Realiza a lógica de empréstimo.
*   `POST /biblioteca/devolucao/{idUsuario}/{idLivro}` - Realiza a lógica de devolução.
*   `GET /usuarios/{id}` - Busca e exibe as informações do usuário e a lista de livros que ele pegou emprestado.
*   `GET /livros` - Retorna a lista de todos os livros cadastrados, mostrando a quais usuários eles estão associados (ou `null` se livres).

---

## O Que Será Avaliado (Objetivos de Revisão)

1.  **Encapsulamento e Lombok**: Uso correto de modificadores de acesso (`private`) e anotações como `@Getter`, `@Setter`, `@NoArgsConstructor`, etc.
2.  **Equals e HashCode**: Implementação segura em JPA usando a anotação `@EqualsAndHashCode(onlyExplicitlyIncluded = true)` no atributo `id`.
3.  **Transacionalidade**: Utilização da anotação `@Transactional` nos métodos do serviço para garantir a consistência das operações no banco de dados.
4.  **Manejo de Erros (Exception Handling)**: Criação de um manipulador global (`@RestControllerAdvice`) para capturar exceções de negócio e retornar status HTTP condizentes com o erro (ex: `400 Bad Request` em regras violadas e `404 Not Found` para IDs não encontrados).

---

## Estrutura Sugerida para Testar a API

Inicie o servidor Spring Boot e realize as seguintes requisições (com Postman, Insomnia ou curl) para validar se o sistema está operando corretamente:

1.  **Cadastrar livros** (ex: *Código Limpo*, *Arquitetura Limpa*, *Refatoração* e *Padrões de Projeto*) via `POST /livros`.
2.  **Cadastrar um usuário** via `POST /usuarios`.
3.  **Simular empréstimo bem-sucedido** via `POST /biblioteca/emprestimo/{idUsuario}/{idLivro}`.
4.  **Simular tentativa de pegar um livro já emprestado**: Tente pegar o mesmo livro para outro usuário (Deve retornar erro `400 Bad Request`).
5.  **Simular limite de empréstimos**: Faça o mesmo usuário pegar 3 livros emprestados com sucesso. Tente fazer ele pegar o 4º livro (Deve retornar erro de limite atingido).
6.  **Simular devolução bem-sucedida** via `POST /biblioteca/devolucao/{idUsuario}/{idLivro}`.
7.  **Consultar usuário** via `GET /usuarios/{id}` e verificar se a lista de livros foi atualizada tanto após o empréstimo quanto após a devolução.
