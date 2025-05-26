package br.com.vidaplus.sghss.dto.request;

import jakarta.validation.constraints.NotNull;

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
