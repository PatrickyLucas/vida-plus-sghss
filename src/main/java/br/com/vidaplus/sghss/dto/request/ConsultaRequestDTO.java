package br.com.vidaplus.sghss.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO para requisição de consulta médica.
 * Contém informações sobre o paciente, profissional de saúde, data e status da consulta.
 *
 * @author Patricky Lucas
 */
public class ConsultaRequestDTO {

    @NotNull(message = "O ID do paciente é obrigatório")
    private Long pacienteId;

    @NotNull(message = "O ID do profissional é obrigatório")
    private Long profissionalId;

    @NotNull(message = "A data da consulta é obrigatória")
    @Future(message = "A data da consulta deve ser no futuro")
    private LocalDateTime data;

    @NotBlank(message = "O status da consulta é obrigatório")
    private String status;

    // Getters e Setters
    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
