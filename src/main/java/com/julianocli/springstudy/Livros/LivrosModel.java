package com.julianocli.springstudy.Livros;

import com.julianocli.springstudy.User.UserModel;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_livros")
public class LivrosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private Long id;
    private String titulo;

    @ManyToOne // Muitos livros podem ter apenas uma pessoa
    @JoinColumn(name = "user_id") // Chave Estrangeira
@JsonBackReference
    private UserModel user;


    public LivrosModel(String titulo) {
        this.titulo = titulo;
    }

}
