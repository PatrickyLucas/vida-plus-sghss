package br.com.vidaplus.sghss.dto.response;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para representar a resposta de uma consulta.
 * Contém informações sobre a consulta, como ID, status, data, paciente e profissional envolvidos.
 */
public class ConsultaResponseDTO {

    private Long id;
    private String status;
    private LocalDateTime data;
    private Long pacienteId;
    private String pacienteNome;
    private Long profissionalId;
    private String profissionalNome;

    /**
     * Construtor para criar uma instância de ConsultaResponseDTO com os dados fornecidos.
     *
     * @param id              ID da consulta
     * @param status          Status da consulta (ex: "PENDENTE", "CONFIRMADA", "CANCELADA")
     * @param data            Data e hora da consulta
     * @param pacienteId      ID do paciente associado à consulta
     * @param pacienteNome    Nome do paciente associado à consulta
     * @param profissionalId  ID do profissional de saúde associado à consulta
     * @param profissionalNome Nome do profissional de saúde associado à consulta
     */
    public ConsultaResponseDTO(Long id, String status, LocalDateTime data,
                               Long pacienteId, String pacienteNome,
                               Long profissionalId, String profissionalNome) {
        this.id = id;
        this.status = status;
        this.data = data;
        this.pacienteId = pacienteId;
        this.pacienteNome = pacienteNome;
        this.profissionalId = profissionalId;
        this.profissionalNome = profissionalNome;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public String getPacienteNome() {
        return pacienteNome;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public String getProfissionalNome() {
        return profissionalNome;
    }
}
