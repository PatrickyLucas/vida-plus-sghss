package br.com.vidaplus.sghss.dto.request;

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
public class AuthRequest {
    private String username;
    private String password;


}