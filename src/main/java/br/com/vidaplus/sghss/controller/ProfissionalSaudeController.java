package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.request.PacienteRequestDTO;
import br.com.vidaplus.sghss.dto.request.ProfissionalSaudeRequestDTO;
import br.com.vidaplus.sghss.dto.response.PacienteResponseDTO;
import br.com.vidaplus.sghss.dto.response.ProfissionalSaudeResponseDTO;
import br.com.vidaplus.sghss.mapper.PacienteMapper;
import br.com.vidaplus.sghss.mapper.ProfissionalSaudeMapper;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.service.ProfissionalSaudeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Controlador REST para operações relacionadas a Profissionais de Saúde.
 * Disponibiliza endpoints para listar, buscar, criar, atualizar e excluir profissionais de saúde.
 */
@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalSaudeController {

    /**
     * Serviço de ProfissionalSaude utilizado para as operações CRUD.
     */
    private final ProfissionalSaudeService profissionalSaudeService;
    private final ProfissionalSaudeMapper profissionalSaudeMapper;

    /**
     * Construtor do ProfissionalSaudeController.
     *
     * @param profissionalSaudeService serviço de profissional de saúde
     * @param profissionalSaudeMapper   mapeador para conversão entre entidades e DTOs
     */
    public ProfissionalSaudeController(ProfissionalSaudeService profissionalSaudeService,
                                       ProfissionalSaudeMapper profissionalSaudeMapper) {
        this.profissionalSaudeService = profissionalSaudeService;
        this.profissionalSaudeMapper = profissionalSaudeMapper;
    }

    /**
     * Lista todos os profissionais de saúde.
     *
     * @return lista de ProfissionalSaudeResponseDTO
     */
    @GetMapping
    public ResponseEntity<List<ProfissionalSaudeResponseDTO>> listarTodos() {
        List<ProfissionalSaude> profissionais = profissionalSaudeService.listarTodos();
        List<ProfissionalSaudeResponseDTO> response = profissionais.stream()
                .map(profissionalSaudeMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Busca um profissional de saúde pelo ID.
     *
     * @param id ID do profissional de saúde
     * @return ProfissionalSaudeResponseDTO ou 404 Not Found se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalSaudeResponseDTO> buscarPorId(@PathVariable Long id) {
        return profissionalSaudeService.buscarPorId(id)
                .map(profissionalSaudeMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo profissional de saúde.
     *
     * @param dto dados do profissional de saúde a serem criados
     * @return ProfissionalSaudeResponseDTO do profissional criado
     */
    @PostMapping
    public ResponseEntity<ProfissionalSaudeResponseDTO> salvarProfissional(
            @RequestBody @Valid ProfissionalSaudeRequestDTO dto) {

        ProfissionalSaude profissional = profissionalSaudeMapper.toEntity(dto);
        ProfissionalSaude salvo = profissionalSaudeService.salvarProfissional(profissional);
        ProfissionalSaudeResponseDTO response = profissionalSaudeMapper.toResponseDTO(salvo);

        return ResponseEntity.ok(response);
    }

    /**
     * Exclui um profissional de saúde pelo ID.
     *
     * @param id ID do profissional de saúde a ser excluído
     * @return 204 No Content se excluído com sucesso
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProfissional(@PathVariable Long id) {
        profissionalSaudeService.excluirProfissional(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza um profissional de saúde existente.
     *
     * @param id ID do profissional de saúde a ser atualizado
     * @param requestDTO dados atualizados do profissional de saúde
     * @return ProfissionalSaudeResponseDTO atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalSaudeResponseDTO> atualizarProfissionalSaude(@PathVariable Long id, @Valid @RequestBody ProfissionalSaudeRequestDTO requestDTO) {
        ProfissionalSaude profissionalSaudeAtualizado = profissionalSaudeService.atualizarProfissionalSaude(id, requestDTO);
        return ResponseEntity.ok(profissionalSaudeMapper.toResponseDTO(profissionalSaudeAtualizado));
    }
}
