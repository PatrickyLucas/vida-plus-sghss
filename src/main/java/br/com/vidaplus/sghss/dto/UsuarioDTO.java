package br.com.vidaplus.sghss.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) para representar um usuário no sistema.
 * Este DTO é usado para transferir dados de entrada e saída relacionados a usuários.
 *
 * @author Patricky Lucas
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "DTO para representar um usuário no sistema",
        title = "Usuário DTO")
public class UsuarioDTO {
    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 4, max = 50, message = "O nome de usuário deve ter entre 4 e 50 caracteres")
    @Schema(description = "Nome de usuário para autenticação", example = "usuario123")
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    @Schema(description = "Senha para autenticação do usuário", example = "senhaSegura123")
    private String password;

    @NotBlank(message = "O nome do papel é obrigatório")
    @Size(max = 50, message = "O nome do papel deve ter no máximo 50 caracteres")
    @Schema(description = "Nome do papel ou função do usuário no sistema", example = "ADMIN/MEDICO/PACIENTE")
    private String roleNome;

}