package br.com.vidaplus.sghss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe DTO para representar a resposta do prontuário.
 *
 * @author VidaPlus
 */
@Getter
@Setter
@AllArgsConstructor
public class ProntuarioResponseDTO {

    private Long id;
    private String registros; // agora é string novamente
    private Long pacienteId;
    private String pacienteNome;
}
