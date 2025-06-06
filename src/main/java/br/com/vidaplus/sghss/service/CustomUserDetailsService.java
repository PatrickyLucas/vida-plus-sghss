package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.model.Usuario;
import br.com.vidaplus.sghss.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Serviço personalizado para carregar detalhes do usuário.
 * Implementa a interface UserDetailsService do Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Repositório para acessar os dados do usuário.
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Construtor que recebe o repositório de usuários.
     *
     * @param usuarioRepository Repositório de usuários.
     */
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carrega os detalhes do usuário pelo nome de usuário.
     *
     * @param username Nome de usuário a ser carregado.
     * @return Detalhes do usuário.
     * @throws UsernameNotFoundException Se o usuário não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNome()))
                        .collect(Collectors.toList())
        );
    }
}
