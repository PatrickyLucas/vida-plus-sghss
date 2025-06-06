package br.com.vidaplus.sghss.controller;


import br.com.vidaplus.sghss.dto.response.JwtResponseDTO;
import br.com.vidaplus.sghss.dto.UsuarioDTO;
import br.com.vidaplus.sghss.model.Usuario;
import br.com.vidaplus.sghss.service.CustomUserDetailsService;
import br.com.vidaplus.sghss.security.JwtUtil;
import br.com.vidaplus.sghss.service.AuthService;
import br.com.vidaplus.sghss.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 *  Controller para autenticação e registro de usuários.
 * Fornece endpoints para login e registro de novos usuários.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Serviço de detalhes do usuário personalizado para autenticação.
     */
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    /**
     * Construtor do AuthController.
     *
     * @param customUserDetailsService serviço de detalhes do usuário
     * @param authenticationManager    gerenciador de autenticação
     * @param jwtUtil                  utilitário JWT para geração de tokens
     * @param usuarioService           serviço de usuário para operações relacionadas a usuários
     */
    public AuthController(CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService usuarioService) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint para login de usuários.
     * Recebe um objeto UsuarioDTO contendo username e password,
     * autentica o usuário e retorna um JWT se a autenticação for bem-sucedida.
     *
     * @param usuarioDTO objeto contendo as credenciais do usuário
     * @return ResponseEntity com o token JWT ou erro 401 se a autenticação falhar
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuarioDTO.getUsername(),
                            usuarioDTO.getPassword()
                    )
            );
            // Recupera o UserDetails pelo username
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(usuarioDTO.getUsername());
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponseDTO(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Endpoint para registrar um novo usuário.
     * Recebe um objeto UsuarioDTO contendo os dados do novo usuário,
     * cria o usuário e retorna o objeto criado.
     *
     * @param usuarioDTO objeto contendo os dados do novo usuário
     * @return ResponseEntity com o usuário criado
     */
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario novoUsuario = usuarioService.criarUsuario(
                usuarioDTO.getUsername(),
                usuarioDTO.getPassword(),
                usuarioDTO.getRoleNome()
        );
        return ResponseEntity.ok(novoUsuario);
    }
}