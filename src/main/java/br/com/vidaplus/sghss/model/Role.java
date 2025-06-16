package br.com.vidaplus.sghss.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe que representa um papel (role) no sistema.
 * Cada papel tem um nome único.
 *
 * @author Patricky Lucas
 */
@Entity
@Table(name = "roles")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    public Role(String nome) {
        this.nome = nome;
    }

}
