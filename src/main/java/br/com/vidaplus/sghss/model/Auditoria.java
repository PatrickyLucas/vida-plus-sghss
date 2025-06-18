package br.com.vidaplus.sghss.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Column(nullable = false, length = 50)
    private String usuario;
    @Column(nullable = false)
    private String acao;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String detalhes;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy - HH:mm:ss") // saída JSON
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHora;


}