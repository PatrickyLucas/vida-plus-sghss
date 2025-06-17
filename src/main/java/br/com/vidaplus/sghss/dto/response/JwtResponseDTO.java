package br.com.vidaplus.sghss.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) para representar a resposta de autenticação JWT.
 * Contém o token JWT gerado após a autenticação bem-sucedida.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para resposta de autenticação JWT",
        title = "JWT Response DTO")
public class JwtResponseDTO {
    @Schema(description = "Token JWT gerado após autenticação bem-sucedida", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;


}
