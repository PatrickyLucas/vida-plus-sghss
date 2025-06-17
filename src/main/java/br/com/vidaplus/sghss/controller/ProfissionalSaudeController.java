package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.request.ProfissionalSaudeRequestDTO;
import br.com.vidaplus.sghss.dto.response.ProfissionalSaudeResponseDTO;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.mapper.ProfissionalSaudeMapper;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.service.ProfissionalSaudeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para operações relacionadas a Profissionais de Saúde.
 * Disponibiliza endpoints para listar, buscar, criar, atualizar e excluir profissionais de saúde.
 *
 * @author Patricky Lucas
 */
@RestController
@RequestMapping("/api/profissionais")
@Tag(name = "Profissionais de Saúde", description = "Operações relacionadas a Profissionais de Saúde")
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
     * @param profissionalSaudeMapper mapeador para conversão entre entidades e DTOs
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
    @Operation(
            summary = "Listar Profissionais de Saúde",
            description = "Lista todos os profissionais de saúde registrados no sistema."
    )
    public ResponseEntity<List<ProfissionalSaudeResponseDTO>> listarTodos() {
        List<ProfissionalSaude> profissionais = profissionalSaudeService.listarTodos();
        List<ProfissionalSaudeResponseDTO> response = profissionais.stream()
                .map(profissionalSaudeMapper::toResponseDTO)
                .collect(Collectors.toList());
        // lança uma exceção se a lista estiver vazia
        if (response.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum profissional de saúde encontrado.");
        }
        return ResponseEntity.ok(response);
    }
    /**
     * Busca um profissional de saúde pelo ID.
     *
     * @param id ID do profissional de saúde a ser buscado
     * @return ProfissionalSaudeResponseDTO do profissional encontrado ou uma exceção se não encontrado
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar Profissional de Saúde por ID",
            description = "Busca um profissional de saúde pelo ID fornecido. " +
                    "Retorna uma exceção se o profissional não for encontrado."
    )
    public ResponseEntity<ProfissionalSaudeResponseDTO> buscarPorId(@PathVariable Long id) {
        return profissionalSaudeService.buscarPorId(id)
                .map(profissionalSaudeMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional de saúde com ID " + id + " não encontrado."));
    }
    /**
     * Cria um novo profissional de saúde.
     *
     * @param dto dados do profissional de saúde a ser criado
     * @return ProfissionalSaudeResponseDTO do profissional criado
     */
    @PostMapping
    @Operation(
            summary = "Criar Profissional de Saúde",
            description = "Cria um novo profissional de saúde com os dados fornecidos."
    )
    public ResponseEntity<ProfissionalSaudeResponseDTO> salvarProfissional(
            @RequestBody @Valid ProfissionalSaudeRequestDTO dto) {

        ProfissionalSaude profissional = profissionalSaudeMapper.toEntity(dto);
        ProfissionalSaude salvo = profissionalSaudeService.salvarProfissional(profissional);
        ProfissionalSaudeResponseDTO response = profissionalSaudeMapper.toResponseDTO(salvo);
        // Lança uma exceção se o profissional não for salvo corretamente
        if (response == null) {
            throw new RecursoNaoEncontradoException("Erro ao salvar o profissional de saúde.");
        }
        return ResponseEntity.ok(response);
    }
    /**
     * Exclui um profissional de saúde pelo ID.
     *
     * @param id ID do profissional de saúde a ser excluído
     * @return ResponseEntity com status 204 No Content se a exclusão for bem-sucedida
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir Profissional de Saúde",
            description = "Exclui um profissional de saúde pelo ID fornecido. " +
                    "Retorna 204 No Content se a exclusão for bem-sucedida."
    )
    public ResponseEntity<Void> excluirProfissional(@PathVariable Long id) {
        // Verifica se o profissional existe antes de tentar excluir
        if (!profissionalSaudeService.existePorId(id)) {
            throw new RecursoNaoEncontradoException("Profissional de saúde com ID " + id + " não encontrado.");
        }
        profissionalSaudeService.excluirProfissional(id);
        return ResponseEntity.noContent().build();
    }
    /**
     * Atualiza um profissional de saúde existente.
     *
     * @param id ID do profissional de saúde a ser atualizado
     * @param requestDTO dados atualizados do profissional de saúde
     * @return ProfissionalSaudeResponseDTO do profissional atualizado
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar Profissional de Saúde",
            description = "Atualiza os dados de um profissional de saúde existente. " +
                    "Retorna uma exceção se o profissional não for encontrado."
    )
    public ResponseEntity<ProfissionalSaudeResponseDTO> atualizarProfissionalSaude(@PathVariable Long id, @Valid @RequestBody ProfissionalSaudeRequestDTO requestDTO) {
        ProfissionalSaude profissionalSaudeAtualizado = profissionalSaudeService.atualizarProfissionalSaude(id, requestDTO);
        // Lança uma exceção se o profissional não for encontrado
        if (profissionalSaudeAtualizado == null) {
            throw new RecursoNaoEncontradoException("Profissional de saúde com ID " + id + " não encontrado.");
        }
        return ResponseEntity.ok(profissionalSaudeMapper.toResponseDTO(profissionalSaudeAtualizado));
    }
}
