package br.com.vidaplus.sghss.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        description = "DTO para resposta de auditoria",
        title = "Auditoria Response DTO")
public class AuditoriaResponseDTO {
    @Schema(description = "ID da auditoria", example = "1")
    private Long id;
    @Schema(description = "Usuário que realizou a ação", example = "usuario123")
    private String usuario;
    @Schema(description = "Entidade afetada pela ação", example = "Paciente")
    private String entidade;
    @Schema(description = "Ação realizada", example = "CREATE/UPDATE/DELETE")
    private String acao;
    @Schema(description = "Detalhes da ação realizada", example = "Paciente criado com sucesso")
    private String detalhes;
    @JsonFormat(pattern = "dd/MM/yyyy - HH:mm:ss")
    @Schema(description = "Data e hora da ação", example = "01/12/2023 - 10:00:00")
    private LocalDateTime dataHora;

}
