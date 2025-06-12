package br.com.vidaplus.sghss.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * DTO para requisição de prontuário.
 * Contém informações sobre o paciente e os registros médicos.
 *
 * @author Patricky Lucas
 */
public class ProntuarioRequestDTO {

    @NotNull
    private Long pacienteId;

    @NotNull
    private String registros;

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getRegistros() {
        return registros;
    }

    public void setRegistros(String registros) {
        this.registros = registros;
    }
}
