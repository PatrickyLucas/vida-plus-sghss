package br.com.vidaplus.sghss.dto;

public class UsuarioDTO {
    private String username;
    private String password;
    private String roleNome;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRoleNome() { return roleNome; }
    public void setRoleNome(String roleNome) { this.roleNome = roleNome; }
}