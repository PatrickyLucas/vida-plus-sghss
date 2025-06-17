package br.com.vidaplus.sghss.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe DTO para representar a resposta do prontuário.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para resposta de prontuário",
        title = "Prontuário Response DTO")
public class ProntuarioResponseDTO {

    @Schema(description = "ID do prontuário", example = "1")
    private Long id;
    @Schema(description = "Nome do prontuário", example = "Prontuário de Exemplo")
    private String registros;
    @Schema(description = "ID do paciente associado ao prontuário", example = "123")
    private Long pacienteId;
    @Schema(description = "Nome do paciente associado ao prontuário", example = "João da Silva")
    private String pacienteNome;
}
