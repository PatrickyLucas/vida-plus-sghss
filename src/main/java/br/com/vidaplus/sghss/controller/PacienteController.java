package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.request.PacienteRequestDTO;
import br.com.vidaplus.sghss.dto.response.PacienteResponseDTO;
import br.com.vidaplus.sghss.mapper.PacienteMapper;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    /**
     * Construtor do PacienteController.
     *
     * @param pacienteService serviço de paciente
     */
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * Lista todos os pacientes.
     *
     * @return lista de PacienteResponseDTO
     */
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        List<PacienteResponseDTO> dtos = pacientes.stream()
                .map(PacienteMapper::toResponseDTO)
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
        return pacienteService.buscarPorId(id)
                .map(paciente -> ResponseEntity.ok(PacienteMapper.toResponseDTO(paciente)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo paciente.
     *
     * @param requestDTO dados do paciente a serem criados
     * @return PacienteResponseDTO do paciente criado
     */
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> salvarPaciente(@Valid @RequestBody PacienteRequestDTO requestDTO) {
        Paciente paciente = PacienteMapper.toEntity(requestDTO);
        Paciente salvo = pacienteService.salvarPaciente(paciente);
        return ResponseEntity.ok(PacienteMapper.toResponseDTO(salvo));
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
        return ResponseEntity.ok(PacienteMapper.toResponseDTO(pacienteAtualizado));
    }
}
