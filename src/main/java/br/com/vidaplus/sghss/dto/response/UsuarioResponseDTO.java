package br.com.vidaplus.sghss.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;


/**
 * DTO para resposta de usuário.
 * Contém informações básicas do usuário, como ID, nome de usuário e roles.
 *
 * @author Patricky Lucas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Schema(
        description = "DTO para resposta de usuário",
        title = "Usuário Response DTO")
public class UsuarioResponseDTO {
    @Schema(description = "ID do usuário", example = "1")
    private Long id;
    @Schema(description = "Nome de usuário", example = "usuario123")
    private String username;
    @Schema(description = "Lista de roles do usuário", example = "ROLE_ADMIN")
    private List<String> roles;
}
