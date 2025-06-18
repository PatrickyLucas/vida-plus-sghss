package br.com.vidaplus.sghss.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
@Schema(
        description = "DTO para requisição de consulta médica",
        title = "Consulta Request DTO")
public class ConsultaRequestDTO {

    @NotNull(message = "O ID do paciente é obrigatório")
    @Schema(description = "ID do paciente associado à consulta", example = "123")
    private Long pacienteId;

    @NotNull(message = "O ID do profissional é obrigatório")
    @Schema(description = "ID do profissional de saúde que realizará a consulta", example = "456")
    private Long profissionalId;

    @NotNull(message = "A data da consulta é obrigatória")
    @Future(message = "A data da consulta deve ser no futuro")
    @Schema(description = "Data e hora da consulta médica, deve ser uma data futura", example = "01/12/2023 - 10:00:00")
    @JsonFormat(pattern = "dd/MM/yyyy - HH:mm:ss")
    @DateTimeFormat(pattern = "dd/MM/yyyy - HH:mm:ss")
    private LocalDateTime dataHora;

    @NotBlank(message = "O status da consulta é obrigatório")
    @Schema(description = "Status da consulta médica", example = "AGENDADA/CONCLUIDA/CANCELADA")
    private String status;

}
