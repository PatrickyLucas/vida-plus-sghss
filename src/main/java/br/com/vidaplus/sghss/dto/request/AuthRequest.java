package br.com.vidaplus.sghss.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para requisição de autenticação.
 * Contém informações de usuário e senha.
 */
@Getter
@Setter
public class AuthRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}