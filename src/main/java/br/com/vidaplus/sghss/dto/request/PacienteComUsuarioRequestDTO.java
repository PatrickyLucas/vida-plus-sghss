package br.com.vidaplus.sghss.dto.request;

import br.com.vidaplus.sghss.dto.UsuarioDTO;
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
public class PacienteComUsuarioRequestDTO {
    private PacienteRequestDTO paciente;
    private UsuarioDTO usuario;
}
