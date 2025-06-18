package br.com.vidaplus.sghss.mapper;

import br.com.vidaplus.sghss.dto.response.UsuarioResponseDTO;
import br.com.vidaplus.sghss.model.Role;
import br.com.vidaplus.sghss.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para converter entre DTOs de usuário e entidades de usuário.
 * Utilizado para transformar dados entre a camada de apresentação e a camada de persistência.
 *
 * @author Patricky Lucas
 */
public class UsuarioMapper {
    /**
     * Converte uma entidade Usuario para um DTO de resposta UsuarioResponseDTO.
     *
     * @param usuario a entidade Usuario a ser convertida
     * @return um DTO de resposta contendo os dados do usuário
     */
    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        List<String> roles = usuario.getRoles().stream()
                .map(Role::getNome)
                .collect(Collectors.toList());

        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), roles);
    }

    /**
     * Converte um DTO de resposta UsuarioResponseDTO para uma entidade Usuario.
     *
     * @param dto o DTO de resposta contendo os dados do usuário
     * @return uma instância de Usuario com os dados do DTO
     */
    public static Usuario toEntity(UsuarioResponseDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setUsername(dto.getUsername());
        return usuario;
    }
}
