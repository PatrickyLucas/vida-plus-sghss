package br.com.vidaplus.sghss.controller;


import br.com.vidaplus.sghss.dto.request.PacienteComUsuarioRequestDTO;
import br.com.vidaplus.sghss.dto.request.ProfissionalSaudeComUsuarioRequestDTO;
import br.com.vidaplus.sghss.dto.response.JwtResponseDTO;
import br.com.vidaplus.sghss.dto.request.UsuarioDTO;
import br.com.vidaplus.sghss.dto.response.PacienteResponseDTO;
import br.com.vidaplus.sghss.dto.response.ProfissionalSaudeResponseDTO;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.exception.UsuarioSemPermissaoException;
import br.com.vidaplus.sghss.mapper.PacienteMapper;
import br.com.vidaplus.sghss.mapper.ProfissionalSaudeMapper;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.model.Usuario;
import br.com.vidaplus.sghss.service.*;
import br.com.vidaplus.sghss.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 *  Controller para autenticação e registro de usuários.
 * Fornece endpoints para login e registro de novos usuários.
 *
 * @author Patricky Lucas
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Operações de autenticação e registro de usuários")
public class AuthController {

    /**
     * Serviço de detalhes do usuário personalizado para autenticação.
     */

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
     * @return ResponseEntity com o token JWT ou uma excption se a autenticação falhar
     */
    @PostMapping("/login")
    @Operation(
            summary = "Login de Usuário",
            description = "Realiza o login do usuário e retorna um token JWT. " +
                    "Se as credenciais estiverem incorretas, retorna uma exceção de usuario sem permissão."
    )
    public ResponseEntity<JwtResponseDTO> login(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            JwtResponseDTO jwt = authService.login(usuarioDTO.getUsername(), usuarioDTO.getPassword());
            return ResponseEntity.ok(jwt);
        } catch (UsuarioSemPermissaoException e) {
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
    @PostMapping("/registrar-admin")
    @Operation(
            summary = "Registrar Usuário Administrador",
            description = "Registra um novo admin no sistema. " +
                    "O usuário deve fornecer um nome de usuário, senha e role."
    )
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario novoUsuario = usuarioService.criarUsuario(
                usuarioDTO.getUsername(),
                usuarioDTO.getPassword(),
                usuarioDTO.getRoleNome()
        );
        // Lança uma exceção se o usuário não for criado corretamente
        if (novoUsuario == null) {
            throw new RecursoNaoEncontradoException("Erro ao criar usuário. Verifique os dados fornecidos.");
        }
        return ResponseEntity.ok(novoUsuario);
    }

    /**
     * Endpoint para registrar um novo paciente com usuário.
     * Recebe um objeto PacienteComUsuarioRequestDTO contendo os dados do paciente e do usuário,
     * cria o paciente e o usuário associado, e retorna o objeto PacienteResponseDTO criado.
     *
     * @param requestDTO objeto contendo os dados do paciente e do usuário
     * @return ResponseEntity com o paciente criado
     */
    @PostMapping("/registrar-paciente")
    @Operation(
            summary = "Registrar Paciente",
            description = "Registra um novo paciente com um usuário associado. " +
                    "O paciente deve fornecer os dados pessoais e o usuário."
    )
    public ResponseEntity<PacienteResponseDTO> registrarPaciente(@RequestBody PacienteComUsuarioRequestDTO requestDTO) {
        Paciente paciente = pacienteService.criarPacienteComUsuario(
                requestDTO.getPaciente(),
                requestDTO.getUsuario()
        );
        // Lança uma exceção se o paciente não for criado corretamente
        if (paciente == null) {
            throw new RecursoNaoEncontradoException("Erro ao criar paciente. Verifique os dados fornecidos.");
        }
        return ResponseEntity.ok(pacienteMapper.toResponseDTO(paciente));
    }

    /**
     * Endpoint para registrar um novo profissional de saúde com usuário.
     * Recebe um objeto ProfissionalSaudeComUsuarioRequestDTO contendo os dados do profissional e do usuário,
     * cria o profissional e o usuário associado, e retorna o objeto ProfissionalSaudeResponseDTO criado.
     *
     * @param requestDTO objeto contendo os dados do profissional e do usuário
     * @return ResponseEntity com o profissional criado
     */
    @PostMapping("/registrar-profissional")
    @Operation(
            summary = "Registrar Profissional de Saúde",
            description = "Registra um novo profissional de saúde com um usuário associado. " +
                    "O profissional deve fornecer os dados pessoais e o usuário."
    )
    public ResponseEntity<ProfissionalSaudeResponseDTO> registrarProfissionalComUsuario(
            @RequestBody @Valid ProfissionalSaudeComUsuarioRequestDTO requestDTO) {
        ProfissionalSaude profissional = profissionalSaudeService.criarProfissionalComUsuario(
                requestDTO.getProfissional(),
                requestDTO.getUsuario()
        );
        // Lança uma exceção se o profissional não for criado corretamente
        if (profissional == null) {
            throw new RecursoNaoEncontradoException("Erro ao criar profissional de saúde. Verifique os dados fornecidos.");
        }
        return ResponseEntity.ok(profissionalSaudeMapper.toResponseDTO(profissional));
    }

    /**
     * Endpoint para excluir um usuário pelo nome de usuário.
     * Verifica se o usuário existe antes de tentar excluir,
     * e retorna uma resposta 204 No Content se a exclusão for bem-sucedida.
     *
     * @param username nome de usuário do usuário a ser excluído
     * @return ResponseEntity<Void> com status 204 No Content
     */
    @DeleteMapping("/excluir-usuario/{username}")
    @Operation(
            summary = "Excluir Usuário",
            description = "Exclui um usuário pelo nome de usuário fornecido. " +
                    "Retorna 204 No Content se a exclusão for bem-sucedida."
    )
    public ResponseEntity<Void> excluirUsuario(@PathVariable String username) {
        // Verifica se o usuário existe antes de tentar excluir
        if (!usuarioService.existePorUsername(username)) {
            throw new RecursoNaoEncontradoException("Usuário com username '" + username + "' não encontrado.");
        }

        // Verifica se o usuário tem permissão para excluir usuários
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new UsuarioSemPermissaoException("Você não tem permissão para excluir usuários.");
        }

        usuarioService.excluirUsuarioPorUsername(username);
        return ResponseEntity.noContent().build();
    }


    /**
     * Endpoint para atualizar um usuário existente.
     * Recebe um objeto UsuarioDTO contendo os dados atualizados do usuário,
     * e retorna o objeto Usuario atualizado.
     *
     * @param username   nome de usuário do usuário a ser atualizado
     * @param usuarioDTO objeto contendo os dados atualizados do usuário
     * @return ResponseEntity com o usuário atualizado
     */
    @PutMapping("/atualizar-usuario/{username}")
    @Operation(
            summary = "Atualizar Usuário",
            description = "Atualiza um usuário existente com os dados fornecidos. " +
                    "O usuário deve fornecer um nome de usuário, senha e role."
    )
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable String username, @RequestBody UsuarioDTO usuarioDTO) {
        // Verifica se o usuário existe antes de tentar atualizar
        if (!usuarioService.existePorUsername(username)) {
            throw new RecursoNaoEncontradoException("Usuário com username '" + username + "' não encontrado.");
        }

        // Verifica se o usuário tem permissão para atualizar usuários
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new UsuarioSemPermissaoException("Você não tem permissão para atualizar usuários.");
        }

        Usuario usuarioAtualizado = usuarioService.atualizarUsuarioPorUsername(username, usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    /**
     * Endpoint para obter uma lista de todos os usuários registrados.
     * Permite acesso apenas para usuários com permissão ADMIN.
     */
    @GetMapping("/usuarios")
    @Operation(
            summary = "Listar Usuários",
            description = "Lista todos os usuários registrados no sistema. " +
                    "Apenas usuários com permissão ADMIN podem acessar este endpoint."
    )
    public ResponseEntity<Iterable<Usuario>> listarUsuarios() {
        // Verifica se o usuário tem permissão de ADMIN
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new UsuarioSemPermissaoException("Você não tem permissão para listar usuários.");
        }

        Iterable<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

}