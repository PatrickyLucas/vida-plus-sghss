package br.com.vidaplus.sghss.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfissionalSaudeRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "Especialidade é obrigatória")
    @Size(max = 50, message = "Especialidade deve ter no máximo 50 caracteres")
    private String especialidade;

    @NotBlank(message = "Registro profissional é obrigatório")
    @Size(max = 20, message = "Registro profissional deve ter no máximo 20 caracteres")
    private String registroProfissional;
}
