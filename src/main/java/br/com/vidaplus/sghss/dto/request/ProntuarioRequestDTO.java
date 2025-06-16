package br.com.vidaplus.sghss.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para requisição de prontuário.
 * Contém informações sobre o paciente e os registros médicos.
 *
 * @author Patricky Lucas
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProntuarioRequestDTO {

    @NotNull
    private Long pacienteId;

    @NotNull
    private String registros;

}
