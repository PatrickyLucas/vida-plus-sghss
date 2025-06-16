package br.com.vidaplus.sghss.dto.response;

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
public class ProntuarioResponseDTO {

    private Long id;
    private String registros;
    private Long pacienteId;
    private String pacienteNome;
}
