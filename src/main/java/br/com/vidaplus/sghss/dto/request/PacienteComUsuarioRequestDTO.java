package br.com.vidaplus.sghss.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para requisição de Paciente com informações de usuário.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para requisição de Paciente com informações de usuário",
        title = "Paciente com Usuário Request DTO")
public class PacienteComUsuarioRequestDTO {
    @Schema(description = "Informações do paciente")
    private PacienteRequestDTO paciente;
    @Schema(description = "Informações do usuário associado ao paciente")
    private UsuarioDTO usuario;
}
