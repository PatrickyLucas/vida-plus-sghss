package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.model.Usuario;
import br.com.vidaplus.sghss.repository.UsuarioRepository;
import br.com.vidaplus.sghss.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public AuthService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Optional<String> autenticarUsuario(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return Optional.of(jwtUtil.generateToken(userDetails)); // <-- Passa o UserDetails
        }

        return Optional.empty();
    }
}
