package br.com.vidaplus.sghss.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para requisição de prontuário.
 * Contém informações sobre o paciente e os registros médicos.
 *
 * @author Patricky Lucas
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para requisição de prontuário",
        title = "Prontuário Request DTO")
public class ProntuarioRequestDTO {

    @NotNull
    @Schema(description = "ID do paciente associado ao prontuário", example = "123")
    private Long pacienteId;

    @NotNull
    @Schema(description = "Descrição detalhada do prontuário", example = "Paciente apresenta sintomas de gripe.")
    private String registros;

}
