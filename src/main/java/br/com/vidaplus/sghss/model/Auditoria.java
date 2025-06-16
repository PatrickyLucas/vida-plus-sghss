package br.com.vidaplus.sghss.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Classe que representa uma auditoria no sistema.
 * Registra ações realizadas por usuários, incluindo detalhes e data/hora da ação.
 *
 * @author Patricky Lucas
 */
@Entity
@Table(name = "auditoria")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;
    private String acao;
    private String detalhes;
    private LocalDateTime dataHora;


}