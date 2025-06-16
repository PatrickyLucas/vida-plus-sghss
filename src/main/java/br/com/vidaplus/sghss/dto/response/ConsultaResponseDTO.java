package br.com.vidaplus.sghss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para representar a resposta de uma consulta.
 * Contém informações sobre a consulta, como ID, status, data, paciente e profissional envolvidos.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaResponseDTO {

    private Long id;
    private String status;
    private LocalDateTime data;
    private Long pacienteId;
    private String pacienteNome;
    private Long profissionalId;
    private String profissionalNome;

}
