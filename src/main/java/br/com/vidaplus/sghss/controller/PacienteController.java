package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.request.PacienteComUsuarioRequestDTO;
import br.com.vidaplus.sghss.dto.request.PacienteRequestDTO;
import br.com.vidaplus.sghss.dto.response.PacienteResponseDTO;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.exception.UsuarioSemPermissaoException;
import br.com.vidaplus.sghss.mapper.PacienteMapper;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para operações relacionadas a Pacientes.
 * Disponibiliza endpoints para listar, buscar, criar, atualizar e excluir pacientes.
 *
 * @author Patricky Lucas
 */
@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Pacientes", description = "Operações relacionadas a Pacientes")
public class PacienteController {

    /**
     * Serviço de Paciente utilizado para as operações CRUD.
     */
    private final PacienteService pacienteService;
    private final PacienteMapper pacienteMapper;
    /**
     * Construtor do PacienteController.
     *
     * @param pacienteService serviço de paciente
     * @param pacienteMapper mapeador para conversão entre entidades e DTOs
     */
    public PacienteController(PacienteService pacienteService, PacienteMapper pacienteMapper) {
        this.pacienteService = pacienteService;
        this.pacienteMapper = pacienteMapper;
    }
    /**
     * Lista todos os pacientes.
     *
     * @return lista de PacienteResponseDTO
     */
    @GetMapping
    @Operation(
            summary = "Listar Pacientes",
            description = "Lista todos os pacientes registrados no sistema. " +
                    "Apenas usuários com permissão ADMIN ou MEDICO podem acessar este endpoint."
    )
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        // Verifica se o usuário é ADMIN ou MEDICO
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOuMedico = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MEDICO"));
        if (!isAdminOuMedico) {
            throw new UsuarioSemPermissaoException("Você não tem permissão para acessar esta lista.");
        }
        List<Paciente> pacientes = pacienteService.listarTodos();
        List<PacienteResponseDTO> dtos = pacientes.stream()
                .map(pacienteMapper::toResponseDTO)
                .collect(Collectors.toList());
        // Lança uma exceção se a lista estiver vazia
        if (dtos.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum paciente encontrado.");
        }
        return ResponseEntity.ok(dtos);
    }
    /**
     * Busca um paciente pelo ID.
     *
     * @param id ID do paciente a ser buscado
     * @return PacienteResponseDTO do paciente encontrado
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar Paciente por ID",
            description = "Busca um paciente pelo ID fornecido. " +
                    "Permite acesso apenas para usuários ADMIN, MEDICO ou o próprio paciente."
    )
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Paciente paciente = pacienteService.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));

        // Só permite se for ADMIN/MEDICO ou o próprio paciente
        boolean isAdminOuMedico = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MEDICO"));

        if (!isAdminOuMedico && !paciente.getUsuario().getUsername().equals(username)) {
            throw new UsuarioSemPermissaoException("Você não tem permissão para acessar este recurso.");
        }

        return ResponseEntity.ok(pacienteMapper.toResponseDTO(paciente));
    }
    /**
     * Cria um novo paciente com usuário associado.
     *
     * @param requestDTO dados do paciente e usuário a serem criados
     * @return PacienteResponseDTO do paciente criado
     */
    @PostMapping
    @Operation(
            summary = "Criar Paciente",
            description = "Cria um novo paciente com um usuário associado. " +
                    "O paciente deve fornecer os dados pessoais e o usuário."
    )
    public ResponseEntity<PacienteResponseDTO> salvarPaciente(@Valid @RequestBody PacienteComUsuarioRequestDTO requestDTO) {
        Paciente paciente = pacienteService.criarPacienteComUsuario(
                requestDTO.getPaciente(),
                requestDTO.getUsuario()
        );
        // lança uma exceção se o paciente não for criado corretamente
        if (paciente == null) {
            throw new RecursoNaoEncontradoException("Erro ao criar paciente. Verifique os dados fornecidos.");
        }
        return ResponseEntity.ok(pacienteMapper.toResponseDTO(paciente));
    }
    /**
     * Exclui um paciente pelo ID.
     *
     * @param id ID do paciente a ser excluído
     * @return ResponseEntity com status 204 No Content
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir Paciente",
            description = "Exclui um paciente pelo ID fornecido. " +
                    "Permite acesso apenas para usuários ADMIN."
    )
    public ResponseEntity<Void> excluirPaciente(@PathVariable Long id) {
        pacienteService.excluirPaciente(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Atualiza um paciente existente.
     *
     * @param id ID do paciente a ser atualizado
     * @param requestDTO dados atualizados do paciente
     * @return PacienteResponseDTO do paciente atualizado
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar Paciente",
            description = "Atualiza um paciente existente com os dados fornecidos. " +
                    "Permite acesso apenas para usuários ADMIN."
    )
    public ResponseEntity<PacienteResponseDTO> atualizarPaciente(@PathVariable Long id, @Valid @RequestBody PacienteRequestDTO requestDTO) {
        Paciente pacienteAtualizado = pacienteService.atualizarPaciente(id, requestDTO);
        // Lança uma exceção se o paciente não for encontrado
        if (pacienteAtualizado == null) {
            throw new RecursoNaoEncontradoException("Paciente com ID " + id + " não encontrado.");
        }
        return ResponseEntity.ok(pacienteMapper.toResponseDTO(pacienteAtualizado));
    }
}
