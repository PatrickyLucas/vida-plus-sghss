package br.com.vidaplus.sghss.dto.response;

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
public class JwtResponseDTO {
    private String token;


}
