package br.com.vidaplus.sghss.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) para representar um usuário no sistema.
 * Este DTO é usado para transferir dados de entrada e saída relacionados a usuários.
 */
public class UsuarioDTO {
    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 4, max = 50, message = "O nome de usuário deve ter entre 4 e 50 caracteres")
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String password;

    @NotBlank(message = "O nome do papel é obrigatório")
    private String roleNome;


    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRoleNome() { return roleNome; }
    public void setRoleNome(String roleNome) { this.roleNome = roleNome; }
}