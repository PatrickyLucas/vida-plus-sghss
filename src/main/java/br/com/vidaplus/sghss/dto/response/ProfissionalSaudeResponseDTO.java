package br.com.vidaplus.sghss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) para representar a resposta de um profissional de saúde.
 * Contém informações básicas como ID, nome, especialidade e registro profissional.
 *
 * @author Patricky Lucas
 */
@Getter
@Setter
@AllArgsConstructor
public class ProfissionalSaudeResponseDTO {

    private Long id;
    private String nome;
    private String especialidade;
    private String registroProfissional;
}
