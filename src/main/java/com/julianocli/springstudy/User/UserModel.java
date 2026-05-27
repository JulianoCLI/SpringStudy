package com.julianocli.springstudy.User;

import com.julianocli.springstudy.Livros.LivrosModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity // Tranforma uma classe numa entidade do BD
@Table(name = "tb_cadastro") //Cria e usa uma tabela chamada tb_cadastro
public class UserModel {
    // A Model gerencia dados, regras de negócio, lógica e funções do sistema (Conversa com o BD, valida informações e processa elas)

    @Id // Mostra para o SpringBoot que possui um ‘id’
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Mostra que quer que o ‘id’ seja um valor gerado e que o tipo de geração é identity
    @Setter(AccessLevel.NONE) // Diz que não deve ter ‘setter’ para ‘id’
    private long id;

    private String name;
    private String email;
    private int idade;

    @OneToMany (mappedBy = "user")// Uma pessoa pode ter vários livros
    private List<LivrosModel> livros = new ArrayList<>();

    public UserModel(String name, String email, int idade) {
        this.name = name;
        this.email = email;
        this.idade = idade;
    }
}

// A ideia é que uma pessoa possa ter vários livros, porém, um livro só pode ter uma pessoa
