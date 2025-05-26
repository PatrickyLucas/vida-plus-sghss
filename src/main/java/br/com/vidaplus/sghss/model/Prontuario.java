package br.com.vidaplus.sghss.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "prontuarios")
@EntityListeners(AuditingEntityListener.class) // Habilita auditoria
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonProperty("paciente")
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @JsonProperty("registros")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String registros;

    @LastModifiedDate
    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    public Prontuario(Long id, String registros, Paciente paciente) {
        this.id = id;
        this.registros = registros;
        this.paciente = paciente;
    }
}
