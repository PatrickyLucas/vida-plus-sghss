package br.com.vidaplus.sghss.dto.request;

import br.com.vidaplus.sghss.dto.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para requisição de Paciente com informações de usuário.
 */
@Getter
@Setter
public class PacienteComUsuarioRequestDTO {
    private PacienteRequestDTO paciente;
    private UsuarioDTO usuario;
}
