package br.com.vidaplus.sghss.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profissionais")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProfissionalSaude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("nome")
    @Column(nullable = false, length = 100)
    private String nome;

    @JsonProperty("especialidade")
    @Column(nullable = false, length = 50)
    private String especialidade;

    @JsonProperty("registroProfissional")
    @Column(unique = true, nullable = false, length = 20)
    private String registroProfissional;
}