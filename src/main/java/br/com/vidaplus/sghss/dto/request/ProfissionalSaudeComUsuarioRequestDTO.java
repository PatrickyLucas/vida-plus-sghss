package br.com.vidaplus.sghss.dto.request;

import br.com.vidaplus.sghss.dto.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para requisição de Profissional de Saúde com informações de usuário.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
public class ProfissionalSaudeComUsuarioRequestDTO {
    @Valid
    private ProfissionalSaudeRequestDTO profissional;
    @Valid
    private UsuarioDTO usuario;
}