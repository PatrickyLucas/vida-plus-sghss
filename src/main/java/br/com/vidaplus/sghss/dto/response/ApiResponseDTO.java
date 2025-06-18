package br.com.vidaplus.sghss.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Schema(
        description = "DTO para resposta de API",
        title = "Api Response DTO")
public class ApiResponseDTO {
    @Schema(description = "Status da resposta", example = "200")
    private int status;
    @Schema(description = "Mensagem da resposta", example = "Operação realizada com sucesso")
    private String mensagem;
    @Schema(description = "Data e hora da ação", example = "01/12/2023 - 10:00:00")
    private LocalDateTime dataHora;

    public ApiResponseDTO(int value, String s) {
        this.status = value;
        this.mensagem = s;
        this.dataHora = LocalDateTime.now();
    }
}
