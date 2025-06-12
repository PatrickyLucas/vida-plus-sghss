package br.com.vidaplus.sghss.controller;


import br.com.vidaplus.sghss.dto.request.PacienteComUsuarioRequestDTO;
import br.com.vidaplus.sghss.dto.request.ProfissionalSaudeComUsuarioRequestDTO;
import br.com.vidaplus.sghss.dto.response.JwtResponseDTO;
import br.com.vidaplus.sghss.dto.UsuarioDTO;
import br.com.vidaplus.sghss.dto.response.PacienteResponseDTO;
import br.com.vidaplus.sghss.dto.response.ProfissionalSaudeResponseDTO;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.mapper.PacienteMapper;
import br.com.vidaplus.sghss.mapper.ProfissionalSaudeMapper;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.model.Usuario;
import br.com.vidaplus.sghss.service.*;
import br.com.vidaplus.sghss.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
    private final AuthService authService;
    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;
    private final ProfissionalSaudeService profissionalSaudeService;
    private final ProfissionalSaudeMapper profissionalSaudeMapper;

    /**
     * Construtor do AuthController.
     *
     * @param customUserDetailsService serviço de detalhes do usuário
     * @param authenticationManager    gerenciador de autenticação
     * @param jwtUtil                  utilitário JWT para geração de tokens
     * @param usuarioService           serviço de usuário para operações relacionadas a usuários
     */
    public AuthController(CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService usuarioService, AuthService authService, PacienteService pacienteService, PacienteMapper pacienteMapper, ProfissionalSaudeService profissionalSaudeService, ProfissionalSaudeMapper profissionalSaudeMapper) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
        this.authService = authService;
        this.pacienteService = pacienteService;
        this.pacienteMapper = pacienteMapper;
        this.profissionalSaudeService = profissionalSaudeService;
        this.profissionalSaudeMapper = profissionalSaudeMapper;
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
            JwtResponseDTO jwt = authService.login(usuarioDTO.getUsername(), usuarioDTO.getPassword());
            return ResponseEntity.ok(jwt);
        } catch (RecursoNaoEncontradoException e) {
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

    @PostMapping("/registrar-paciente")
    public ResponseEntity<PacienteResponseDTO> registrarPaciente(@RequestBody PacienteComUsuarioRequestDTO requestDTO) {
        Paciente paciente = pacienteService.criarPacienteComUsuario(
                requestDTO.getPaciente(),
                requestDTO.getUsuario()
        );
        // Use o mapper para retornar o DTO de resposta
        return ResponseEntity.ok(pacienteMapper.toResponseDTO(paciente));
    }

    @PostMapping("/registrar-profissional")
    public ResponseEntity<ProfissionalSaudeResponseDTO> registrarProfissionalComUsuario(
            @RequestBody @Valid ProfissionalSaudeComUsuarioRequestDTO requestDTO) {
        ProfissionalSaude profissional = profissionalSaudeService.criarProfissionalComUsuario(
                requestDTO.getProfissional(),
                requestDTO.getUsuario()
        );
        return ResponseEntity.ok(profissionalSaudeMapper.toResponseDTO(profissional));
    }
}