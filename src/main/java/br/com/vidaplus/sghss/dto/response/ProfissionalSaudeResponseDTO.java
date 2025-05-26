package br.com.vidaplus.sghss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfissionalSaudeResponseDTO {

    private Long id;
    private String nome;
    private String especialidade;
    private String registroProfissional;
}
