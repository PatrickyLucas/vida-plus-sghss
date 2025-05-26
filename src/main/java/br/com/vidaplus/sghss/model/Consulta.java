package br.com.vidaplus.sghss.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("paciente")
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JsonProperty("profissional")
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalSaude profissional;


    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime data;

    @JsonProperty("status")
    @Column(nullable = false, length = 20)
    private String status; // Agendada, Concluída, Cancelada


    public Consulta(String status, Long id, Paciente paciente, ProfissionalSaude profissional, LocalDateTime data) {
        this.status = status;
        this.id = id;
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
    }
}