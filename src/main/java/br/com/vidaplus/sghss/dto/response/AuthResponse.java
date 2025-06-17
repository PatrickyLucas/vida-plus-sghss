package br.com.vidaplus.sghss.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) para representar a resposta de autenticação.
 * Contém o token JWT gerado após a autenticação bem-sucedida.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para resposta de autenticação",
        title = "Auth Response DTO")
public class AuthResponse {
    @Schema(description = "Token JWT gerado após autenticação bem-sucedida", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;


}