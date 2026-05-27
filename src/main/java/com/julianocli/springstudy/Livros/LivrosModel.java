package com.julianocli.springstudy.Livros;

import com.julianocli.springstudy.User.UserModel;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_livros")
public class LivrosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Include
    private long id;
    private String titulo;

    @ManyToOne // Muitos livros podem ter apenas uma pessoa
    @JoinColumn(name = "user_id") // Chave Estrangeira
    private UserModel user;


    public LivrosModel(long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

}
