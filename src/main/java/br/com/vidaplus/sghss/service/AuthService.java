package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.model.Usuario;
import br.com.vidaplus.sghss.repository.UsuarioRepository;
import br.com.vidaplus.sghss.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviço de autenticação que lida com o registro e autenticação de usuários.
 * Utiliza o repositório de usuários, utilitário JWT e codificador de senhas.
 */
@Service
public class AuthService {

    /**
     * Repositório de usuários para operações de persistência.
     */
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    /**
     * Construtor do serviço de autenticação.
     *
     * @param usuarioRepository   Repositório de usuários para operações de persistência.
     * @param jwtUtil             Utilitário JWT para geração e validação de tokens.
     * @param userDetailsService  Serviço de detalhes do usuário para autenticação.
     */
    public AuthService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Registra um novo usuário no sistema.
     * A senha é criptografada antes de ser salva.
     *
     * @param usuario O usuário a ser registrado.
     * @return O usuário registrado.
     */
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    /**
     * Autentica um usuário com base no nome de usuário e senha fornecidos.
     * Se a autenticação for bem-sucedida, retorna um token JWT.
     *
     * @param username O nome de usuário do usuário a ser autenticado.
     * @param password A senha do usuário a ser autenticado.
     * @return Um Optional contendo o token JWT se a autenticação for bem-sucedida, ou vazio caso contrário.
     */
    public Optional<String> autenticarUsuario(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return Optional.of(jwtUtil.generateToken(userDetails)); // <-- Passa o UserDetails
        }

        return Optional.empty();
    }
}
