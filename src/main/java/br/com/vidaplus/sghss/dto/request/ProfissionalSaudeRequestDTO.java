package br.com.vidaplus.sghss.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para requisição de Profissional de Saúde.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para requisição de Profissional de Saúde",
        title = "Profissional de Saúde Request DTO")
public class ProfissionalSaudeRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome completo do profissional de saúde", example = "Dr. João da Silva")
    private String nome;

    @NotBlank(message = "Especialidade é obrigatória")
    @Size(max = 50, message = "Especialidade deve ter no máximo 50 caracteres")
    @Schema(description = "Especialidade do profissional de saúde", example = "Cardiologia")
    private String especialidade;

    @NotBlank(message = "Registro profissional é obrigatório")
    @Size(max = 20, message = "Registro profissional deve ter no máximo 20 caracteres")
    @Schema(description = "Número do registro profissional do profissional de saúde", example = "CRM-123456")
    private String registroProfissional;
}
