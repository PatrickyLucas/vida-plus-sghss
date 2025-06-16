package br.com.vidaplus.sghss.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO para requisição de consulta médica.
 * Contém informações sobre o paciente, profissional de saúde, data e status da consulta.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaRequestDTO {

    @NotNull(message = "O ID do paciente é obrigatório")
    private Long pacienteId;

    @NotNull(message = "O ID do profissional é obrigatório")
    private Long profissionalId;

    @NotNull(message = "A data da consulta é obrigatória")
    @Future(message = "A data da consulta deve ser no futuro")
    private LocalDateTime data;

    @NotBlank(message = "O status da consulta é obrigatório")
    private String status;

}
