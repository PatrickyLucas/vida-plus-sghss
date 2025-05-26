package br.com.vidaplus.sghss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProntuarioResponseDTO {

    private Long id;
    private String registros; // agora é string novamente
    private Long pacienteId;
    private String pacienteNome;
}
