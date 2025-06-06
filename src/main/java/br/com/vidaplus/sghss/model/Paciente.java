package br.com.vidaplus.sghss.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("nome")
    @Column(nullable = false, length = 100)
    private String nome;

    @JsonProperty("cpf")
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @JsonProperty("historicoClinico")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String historicoClinico;
}