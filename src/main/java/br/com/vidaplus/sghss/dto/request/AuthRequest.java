package br.com.vidaplus.sghss.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para requisição de autenticação.
 * Contém informações de usuário e senha.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para requisição de autenticação",
        title = "Auth Request DTO")
public class AuthRequest {
    @Schema(description = "Nome de usuário para autenticação", example = "usuario123")
    private String username;
    @Schema(description = "Senha para autenticação", example = "senhaSegura123")
    private String password;


}