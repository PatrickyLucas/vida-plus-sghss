package br.com.vidaplus.sghss.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Classe DTO para requisição de criação ou atualização de um Paciente.
 * Contém validações para os campos obrigatórios e formatos específicos.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para requisição de Paciente",
        title = "Paciente Request DTO")
public class PacienteRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome completo do paciente", example = "João da Silva")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos numéricos")
    @Schema(description = "CPF do paciente, deve conter exatamente 11 dígitos numéricos", example = "12345678901")
    private String cpf;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "A data de nascimento deve ser anterior à data atual")
    @Schema(description = "Data de nascimento do paciente, deve ser uma data passada", example = "1990-01-01")
    private LocalDate dataNascimento;

    @NotBlank(message = "Histórico clínico é obrigatório")
    @Schema(description = "Histórico clínico do paciente, informações relevantes sobre a saúde", example = "Paciente com histórico de hipertensão e diabetes.")
    private String historicoClinico;

}
