package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.request.PacienteComUsuarioRequestDTO;
import br.com.vidaplus.sghss.dto.request.PacienteRequestDTO;
import br.com.vidaplus.sghss.dto.response.PacienteResponseDTO;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.exception.UsuarioSemPermissaoException;
import br.com.vidaplus.sghss.mapper.PacienteMapper;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.service.PacienteService;
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
 */
@RestController
@RequestMapping("/api/pacientes")
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
        return ResponseEntity.ok(dtos);
    }

    /**
     * Busca um paciente pelo ID.
     *
     * @param id ID do paciente
     * @return PacienteResponseDTO ou 404 Not Found se não encontrado
     */
    @GetMapping("/{id}")
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
     * Cria um novo paciente.
     *
     * @param requestDTO dados do paciente a serem criados
     * @return PacienteResponseDTO do paciente criado
     */
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> salvarPaciente(@Valid @RequestBody PacienteComUsuarioRequestDTO requestDTO) {
        Paciente paciente = pacienteService.criarPacienteComUsuario(
                requestDTO.getPaciente(),
                requestDTO.getUsuario()
        );
        return ResponseEntity.ok(pacienteMapper.toResponseDTO(paciente));
    }

    /**
     * Exclui um paciente pelo ID.
     *
     * @param id ID do paciente a ser excluído
     * @return 204 No Content se excluído com sucesso
     */
    @DeleteMapping("/{id}")
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
    public ResponseEntity<PacienteResponseDTO> atualizarPaciente(@PathVariable Long id, @Valid @RequestBody PacienteRequestDTO requestDTO) {
        Paciente pacienteAtualizado = pacienteService.atualizarPaciente(id, requestDTO);
        return ResponseEntity.ok(pacienteMapper.toResponseDTO(pacienteAtualizado));
    }
}
