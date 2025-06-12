package br.com.vidaplus.sghss.dto.response;

/**
 * Data Transfer Object (DTO) para representar a resposta de autenticação JWT.
 * Contém o token JWT gerado após a autenticação bem-sucedida.
 */
public class JwtResponseDTO {
    private String token;

    /**
     * Construtor para criar uma instância de JwtResponseDTO com o token fornecido.
     *
     * @param token o token JWT gerado após a autenticação
     */
    public JwtResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
