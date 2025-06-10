package br.com.vidaplus.sghss.dto.request;

import br.com.vidaplus.sghss.dto.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProfissionalSaudeComUsuarioRequestDTO {
    @Valid
    private ProfissionalSaudeRequestDTO profissional;
    @Valid
    private UsuarioDTO usuario;
}