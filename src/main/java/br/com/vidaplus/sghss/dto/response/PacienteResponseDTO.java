package br.com.vidaplus.sghss.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) para representar a resposta de um paciente.
 * Contém informações básicas como ID, nome, CPF, data de nascimento,
 * histórico clínico e nome de usuário.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para resposta de paciente",
        title = "Paciente Response DTO")
public class PacienteResponseDTO {

    @Schema(description = "ID do paciente", example = "1")
    private Long id;
    @Schema(description = "Nome do paciente", example = "João da Silva")
    private String nome;
    @Schema(description = "CPF do paciente", example = "123.456.789-00")
    private String cpf;
    @Schema(description = "Data de nascimento do paciente", example = "1990-01-01")
    private LocalDate dataNascimento;
    @Schema(description = "Histórico clínico do paciente", example = "Paciente com histórico de hipertensão e diabetes.")
    private String historicoClinico;
    @Schema(description = "Nome de usuário associado ao paciente", example = "joao.silva")
    private String username;
}
