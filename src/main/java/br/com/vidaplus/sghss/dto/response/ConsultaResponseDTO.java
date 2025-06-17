package br.com.vidaplus.sghss.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para representar a resposta de uma consulta.
 * Contém informações sobre a consulta, como ID, status, data, paciente e profissional envolvidos.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para resposta de consulta médica",
        title = "Consulta Response DTO")
public class ConsultaResponseDTO {

    @Schema(description = "ID da consulta", example = "1")
    private Long id;
    @Schema(description = "Status da consulta", example = "AGENDADA/REALIZADA/CANCELADA")
    private String status;
    @Schema(description = "Data e hora da consulta", example = "2023-12-01T10:00:00")
    private LocalDateTime data;
    @Schema(description = "ID do paciente associado à consulta", example = "123")
    private Long pacienteId;
    @Schema(description = "Nome do paciente associado à consulta", example = "João da Silva")
    private String pacienteNome;
    @Schema(description = "ID do profissional de saúde que realizará a consulta", example = "456")
    private Long profissionalId;
    @Schema(description = "Nome do profissional de saúde que realizará a consulta", example = "Dra. Maria Oliveira")
    private String profissionalNome;

}
