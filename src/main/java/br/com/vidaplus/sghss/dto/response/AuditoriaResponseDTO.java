package br.com.vidaplus.sghss.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) para representar a resposta de auditoria.
 * Contém informações sobre a ação realizada, o usuário, a entidade afetada,
 * detalhes da ação e a data e hora em que ocorreu.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditoriaResponseDTO {
    private Long id;
    private String usuario;
    private String entidade;
    private String acao;
    private String detalhes;
    @JsonFormat(pattern = "dd/MM/yyyy - HH:mm:ss")
    private LocalDateTime dataHora;

}
