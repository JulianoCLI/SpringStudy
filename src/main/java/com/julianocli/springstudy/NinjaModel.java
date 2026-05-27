package com.julianocli.springstudy;

import jakarta.persistence.*;


@Entity // Tranforma uma classe numa entidade do BD
@Table(name = "tb_cadastro") //Cria e usa uma tabela chamada tb_cadastro
public class NinjaModel {
    // A Model gerencia dados, regras de negócio, lógica e funções do sistema (Conversa com o BD, valida informações e processa elas)

    @Id // Mostra pro SpringBoot que possui um id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Mostra que quer que o id seja um valor gerado e que o tipo de geração é identity
    private long id;
    private String name;
    private String email;
    private int idade;

    public NinjaModel() {
    }

    public NinjaModel(String name, String email, int idade) {
        this.name = name;
        this.email = email;
        this.idade = idade;
    }
}
