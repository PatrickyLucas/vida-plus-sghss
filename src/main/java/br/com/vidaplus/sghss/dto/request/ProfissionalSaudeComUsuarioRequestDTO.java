package br.com.vidaplus.sghss.dto.request;

import br.com.vidaplus.sghss.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para requisição de Profissional de Saúde com informações de usuário.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para requisição de Profissional de Saúde com informações de usuário",
        title = "Profissional de Saúde com Usuário Request DTO")
public class ProfissionalSaudeComUsuarioRequestDTO {
    @Valid
    @Schema(description = "Informações do profissional de saúde")
    private ProfissionalSaudeRequestDTO profissional;
    @Valid
    @Schema(description = "Informações do usuário associado ao profissional de saúde")
    private UsuarioDTO usuario;
}