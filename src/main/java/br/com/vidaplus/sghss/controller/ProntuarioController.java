package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.request.ProntuarioRequestDTO;
import br.com.vidaplus.sghss.dto.response.ProntuarioResponseDTO;
import br.com.vidaplus.sghss.mapper.ProntuarioMapper;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.Prontuario;
import br.com.vidaplus.sghss.service.PacienteService;
import br.com.vidaplus.sghss.service.ProntuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST para operações relacionadas a Prontuários.
 * Disponibiliza endpoints para listar, buscar, criar, atualizar e excluir prontuários.
 * Apenas usuários com papel MEDICO ou ADMIN podem acessar.
 */
@RestController
@RequestMapping("/api/prontuarios")
@PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
public class ProntuarioController {

    private final ProntuarioService prontuarioService;
    private final PacienteService pacienteService;
    private final ProntuarioMapper prontuarioMapper;

    /**
     * Construtor do ProntuarioController.
     *
     * @param prontuarioService serviço de prontuário
     * @param pacienteService serviço de paciente
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
    public ResponseEntity<List<ProntuarioResponseDTO>> listarProntuarios() {
        List<Prontuario> prontuarios = prontuarioService.listarTodos();
        List<ProntuarioResponseDTO> resposta = prontuarios.stream()
                .map(prontuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

    /**
     * Busca um prontuário pelo ID do paciente.
     *
     * @param pacienteId ID do paciente
     * @return ProntuarioResponseDTO correspondente ou 404 se não encontrado
     */
    @GetMapping("/{pacienteId}")
    public ResponseEntity<ProntuarioResponseDTO> buscarPorPacienteId(@PathVariable Long pacienteId) {
        Optional<Prontuario> prontuario = prontuarioService.buscarPorPacienteId(pacienteId);
        return prontuario
                .map(prontuarioMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo prontuário.
     *
     * @param dto dados do prontuário
     * @return ProntuarioResponseDTO criado
     */
    @PostMapping
    public ResponseEntity<ProntuarioResponseDTO> salvarProntuario(@Valid @RequestBody ProntuarioRequestDTO dto) {
        Paciente paciente = pacienteService.buscarPorId(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Prontuario prontuario = prontuarioMapper.toEntity(dto, paciente);
        Prontuario salvo = prontuarioService.salvarProntuario(prontuario);
        ProntuarioResponseDTO resposta = prontuarioMapper.toResponseDTO(salvo);

        return ResponseEntity.ok(resposta);
    }

    /**
     * Exclui um prontuário pelo ID.
     *
     * @param id ID do prontuário
     * @return resposta sem conteúdo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProntuario(@PathVariable Long id) {
        prontuarioService.excluirProntuario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza um prontuário existente.
     *
     * @param id  ID do prontuário
     * @param dto dados atualizados do prontuário
     * @return ProntuarioResponseDTO atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProntuarioResponseDTO> atualizarProntuario(
            @PathVariable Long id,
            @Valid @RequestBody ProntuarioRequestDTO dto) {

        Prontuario atualizado = prontuarioService.atualizarProntuario(id, dto);
        return ResponseEntity.ok(prontuarioMapper.toResponseDTO(atualizado));
    }
}
