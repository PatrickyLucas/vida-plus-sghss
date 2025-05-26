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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;



    public AuthController(CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService usuarioService) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

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