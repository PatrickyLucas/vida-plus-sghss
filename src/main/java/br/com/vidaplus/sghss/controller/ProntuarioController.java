package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.request.ProntuarioRequestDTO;
import br.com.vidaplus.sghss.dto.response.ApiResponseDTO;
import br.com.vidaplus.sghss.dto.response.ProntuarioResponseDTO;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.exception.UsuarioSemPermissaoException;
import br.com.vidaplus.sghss.mapper.ProntuarioMapper;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.Prontuario;
import br.com.vidaplus.sghss.service.PacienteService;
import br.com.vidaplus.sghss.service.ProntuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para operações relacionadas a Prontuários.
 * Disponibiliza endpoints para listar, buscar, criar, atualizar e excluir prontuários.
 *
 * @author Patricky Lucas
 */
@RestController
@RequestMapping("/api/prontuarios")
@PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
@Tag(name = "Prontuários", description = "Operações relacionadas a Prontuários")
public class ProntuarioController {

    private final ProntuarioService prontuarioService;
    private final PacienteService pacienteService;
    private final ProntuarioMapper prontuarioMapper;

    /**
     * Construtor do ProntuarioController.
     *
     * @param prontuarioService serviço de prontuário
     * @param pacienteService serviço de paciente
     * @param prontuarioMapper mapeador para conversão entre entidades e DTOs
     */
    public ProntuarioController(ProntuarioService prontuarioService, PacienteService pacienteService, ProntuarioMapper prontuarioMapper) {
        this.prontuarioService = prontuarioService;
        this.pacienteService = pacienteService;
        this.prontuarioMapper = prontuarioMapper;
    }
    /**
     * Lista todos os prontuários.
     *
     * @return lista de ProntuarioResponseDTO
     */
    @GetMapping
    @Operation(
            summary = "Listar Prontuários",
            description = "Lista todos os prontuários registrados no sistema. " +
                    "Apenas usuários com permissão ADMIN ou MEDICO podem acessar este endpoint."
    )
    public ResponseEntity<List<ProntuarioResponseDTO>> listarProntuarios() {
        List<Prontuario> prontuarios = prontuarioService.listarTodos();
        List<ProntuarioResponseDTO> resposta = prontuarios.stream()
                .map(prontuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
        // Lança uma exceção se não houver prontuários
        if (resposta.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum prontuário encontrado");
        }
        // Lança uma exceção se o usuário não for ADMIN ou MEDICO
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOuMedico = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MEDICO"));
        if (!isAdminOuMedico) {
            throw new UsuarioSemPermissaoException("Usuário não tem permissão para acessar os prontuários");
        }
        return ResponseEntity.ok(resposta);
    }
    /**
     * Busca um prontuário pelo ID do paciente.
     *
     * @param pacienteId ID do paciente
     * @return ProntuarioResponseDTO do prontuário encontrado ou 404 Not Found se não existir
     */
    @GetMapping("/{pacienteId}")
    @Operation(
            summary = "Buscar Prontuário por Paciente",
            description = "Busca um prontuário pelo ID do paciente. " +
                    "Apenas usuários com permissão ADMIN ou MEDICO podem acessar este endpoint."
    )
    public ResponseEntity<ProntuarioResponseDTO> buscarPorPacienteId(@PathVariable Long pacienteId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Prontuario prontuario = prontuarioService.buscarPorPacienteId(pacienteId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Prontuário não encontrado"));

        boolean isAdminOuMedico = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MEDICO"));

        if (!isAdminOuMedico && !prontuario.getPaciente().getUsuario().getUsername().equals(username)) {
            // retorna uma exceção se o usuário não for ADMIN, MEDICO ou o paciente associado ao prontuário
            throw new UsuarioSemPermissaoException("Usuário não tem permissão para acessar este prontuário");
        }

        return ResponseEntity.ok(prontuarioMapper.toResponseDTO(prontuario));
    }
    /**
     * Cria um novo prontuário.
     *
     * @param dto dados do prontuário a ser criado
     * @return ProntuarioResponseDTO do prontuário criado
     */
    @PostMapping
    @Operation(
            summary = "Criar Prontuário",
            description = "Cria um novo prontuário com os dados fornecidos. " +
                    "O prontuário deve estar associado a um paciente existente."
    )
    public ResponseEntity<ProntuarioResponseDTO> salvarProntuario(@Valid @RequestBody ProntuarioRequestDTO dto) {
        Paciente paciente = pacienteService.buscarPorId(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Prontuario prontuario = prontuarioMapper.toEntity(dto, paciente);
        Prontuario salvo = prontuarioService.salvarProntuario(prontuario);
        ProntuarioResponseDTO resposta = prontuarioMapper.toResponseDTO(salvo);
        // Lança uma exceção se algum recurso não for encontrado
        if (resposta == null) {
            throw new RecursoNaoEncontradoException("Erro ao salvar prontuário");
        }
        return ResponseEntity.ok(resposta);
    }
    /**
     * Exclui um prontuário pelo ID.
     *
     * @param id ID do prontuário a ser excluído
     * @return ResponseEntity com status 204 No Content se a exclusão for bem-sucedida
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir Prontuário",
            description = "Exclui um prontuário pelo ID fornecido. " +
                    "Retorna 204 No Content se a exclusão for bem-sucedida."
    )
    public ResponseEntity<ApiResponseDTO> excluirProntuario(@PathVariable Long id) {
        // Verifica se o prontuário existe antes de tentar excluir
        if (!prontuarioService.existePorId(id)) {
            throw new RecursoNaoEncontradoException("Prontuário não encontrado para exclusão");
        }
        // Verifica se o usuário tem permissão para excluir prontuários
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdminOuMedico = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_MEDICO"));
        if (!isAdminOuMedico) {
            throw new UsuarioSemPermissaoException("Usuário não tem permissão para excluir prontuários");
        }
        prontuarioService.excluirProntuario(id);

        ApiResponseDTO resposta = new ApiResponseDTO(
                HttpStatus.OK.value(),
                "Prontuário com ID " + id + " excluído com sucesso."
        );

        return ResponseEntity.ok(resposta);
    }
    /**
     * Atualiza um prontuário existente.
     *
     * @param id ID do prontuário a ser atualizado
     * @param dto dados do prontuário a serem atualizados
     * @return ProntuarioResponseDTO do prontuário atualizado
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar Prontuário",
            description = "Atualiza um prontuário existente com os dados fornecidos. " +
                    "O prontuário deve estar associado a um paciente existente."
    )
    public ResponseEntity<ProntuarioResponseDTO> atualizarProntuario(
            @PathVariable Long id,
            @Valid @RequestBody ProntuarioRequestDTO dto) {

        Prontuario atualizado = prontuarioService.atualizarProntuario(id, dto);
        // Lança uma exceção se o prontuário não for encontrado para atualização
        if (atualizado == null) {
            throw new RecursoNaoEncontradoException("Prontuário não encontrado para atualização");
        }
        return ResponseEntity.ok(prontuarioMapper.toResponseDTO(atualizado));
    }
}
