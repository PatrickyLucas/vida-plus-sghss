package br.com.vidaplus.sghss.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) para representar a resposta de um profissional de saúde.
 * Contém informações básicas como ID, nome, especialidade e registro profissional.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para resposta de profissional de saúde",
        title = "Profissional Saúde Response DTO")
public class ProfissionalSaudeResponseDTO {

    @Schema(description = "ID do profissional de saúde", example = "1")
    private Long id;
    @Schema(description = "Nome do profissional de saúde", example = "Dr. João da Silva")
    private String nome;
    @Schema(description = "Especialidade do profissional de saúde", example = "Cardiologia")
    private String especialidade;
    @Schema(description = "Número do registro profissional do profissional de saúde", example = "CRM-123456")
    private String registroProfissional;
}
