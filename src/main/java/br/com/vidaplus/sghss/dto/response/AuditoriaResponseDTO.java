package br.com.vidaplus.sghss.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para representar a resposta de auditoria.
 * Contém informações sobre a ação realizada, o usuário, a entidade afetada,
 * detalhes da ação e a data e hora em que ocorreu.
 */
public class AuditoriaResponseDTO {
    private Long id;
    private String usuario;
    private String entidade;
    private String acao;
    private String detalhes;
    @JsonFormat(pattern = "dd/MM/yyyy - HH:mm:ss")
    private LocalDateTime dataHora;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
