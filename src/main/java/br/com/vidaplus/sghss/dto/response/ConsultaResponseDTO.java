package br.com.vidaplus.sghss.dto.response;

import java.time.LocalDateTime;

public class ConsultaResponseDTO {

    private Long id;
    private String status;
    private LocalDateTime data;
    private Long pacienteId;
    private String pacienteNome;
    private Long profissionalId;
    private String profissionalNome;

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
