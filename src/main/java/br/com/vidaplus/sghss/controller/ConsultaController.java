package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.request.ConsultaRequestDTO;
import br.com.vidaplus.sghss.dto.response.ConsultaResponseDTO;
import br.com.vidaplus.sghss.exception.OperacaoNaoPermitidaException;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.mapper.ConsultaMapper;
import br.com.vidaplus.sghss.model.Consulta;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.service.ConsultaService;
import br.com.vidaplus.sghss.service.PacienteService;
import br.com.vidaplus.sghss.service.ProfissionalSaudeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Controlador REST para operações relacionadas a Consultas.
 * Disponibiliza endpoints para listar, buscar, criar, atualizar e excluir consultas.
 */
@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {
    /**
     * Serviço de Consulta utilizado para as operações CRUD.
     */
    private final ConsultaService consultaService;
    private final PacienteService pacienteService;
    private final ProfissionalSaudeService profissionalService;
    private final ConsultaMapper consultaMapper;

    /**
     * Construtor do ConsultaController.
     *
     * @param consultaService serviço de consulta
     * @param pacienteService  serviço de paciente
     * @param profissionalService serviço de profissional de saúde
     * @param consultaMapper   mapeador para conversão entre entidades e DTOs
     */
    public ConsultaController(ConsultaService consultaService, PacienteService pacienteService, ProfissionalSaudeService profissionalService, ConsultaMapper consultaMapper) {
        this.consultaService = consultaService;
        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;
        this.consultaMapper = consultaMapper;
    }

    /**
     * Lista todas as consultas.
     *
     * @return lista de ConsultaResponseDTO
     */
    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listarTodas() {
        List<Consulta> consultas = consultaService.listarTodas();
        List<ConsultaResponseDTO> resposta = consultas.stream()
                .map(consultaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

    /**
     * Busca uma consulta pelo ID.
     *
     * @param id ID da consulta
     * @return ConsultaResponseDTO ou 404 Not Found se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable Long id) {
        return consultaService.buscarPorId(id)
                .map(consultaMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria uma nova consulta.
     *
     * @param requestDTO dados da consulta a serem criados
     * @return ConsultaResponseDTO da consulta criada
     */
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> salvar(@Valid @RequestBody ConsultaRequestDTO requestDTO) {
        Paciente paciente = pacienteService.buscarPorId(requestDTO.getPacienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente com ID " + requestDTO.getPacienteId() + " não encontrado"));

        ProfissionalSaude profissional = profissionalService.buscarPorId(requestDTO.getProfissionalId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional com ID " + requestDTO.getProfissionalId() + " não encontrado"));

        Consulta novaConsulta = consultaMapper.toEntity(requestDTO, paciente, profissional);
        Consulta consultaSalva = consultaService.salvarConsulta(novaConsulta);
        ConsultaResponseDTO resposta = consultaMapper.toResponseDTO(consultaSalva);

        return ResponseEntity.ok(resposta);
    }

    /**
     * Exclui uma consulta pelo ID.
     *
     * @param id ID da consulta a ser excluída
     * @return 204 No Content se excluído com sucesso
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        consultaService.excluirConsulta(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza uma consulta existente.
     *
     * @param id         ID da consulta a ser atualizada
     * @param requestDTO dados atualizados da consulta
     * @return ConsultaResponseDTO da consulta atualizada
     */
    // ConsultaController.java
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ConsultaRequestDTO requestDTO) {
        Paciente paciente = pacienteService.buscarPorId(requestDTO.getPacienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente com ID " + requestDTO.getPacienteId() + " não encontrado"));

        ProfissionalSaude profissional = profissionalService.buscarPorId(requestDTO.getProfissionalId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional com ID " + requestDTO.getProfissionalId() + " não encontrado"));

        Consulta consultaAtualizada = consultaService.atualizarConsulta(id, requestDTO, pacienteService);

        return ResponseEntity.ok(consultaMapper.toResponseDTO(consultaAtualizada));
    }
}
