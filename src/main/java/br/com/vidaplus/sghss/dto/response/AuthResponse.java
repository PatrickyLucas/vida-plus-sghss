package br.com.vidaplus.sghss.dto.response;

import lombok.Getter;

@Getter

/**
 * Data Transfer Object (DTO) para representar a resposta de autenticação.
 * Contém o token JWT gerado após a autenticação bem-sucedida.
 */
public class AuthResponse {
    private String token;

    /**
     * Construtor para criar uma instância de AuthResponse com o token fornecido.
     *
     * @param token o token JWT gerado após a autenticação
     */
    public AuthResponse(String token) {
        this.token = token;
    }
}