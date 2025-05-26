package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.request.ProfissionalSaudeRequestDTO;
import br.com.vidaplus.sghss.dto.response.ProfissionalSaudeResponseDTO;
import br.com.vidaplus.sghss.mapper.ProfissionalSaudeMapper;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.service.ProfissionalSaudeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profissionais")
public class ProfissionalSaudeController {

    private final ProfissionalSaudeService profissionalSaudeService;
    private final ProfissionalSaudeMapper profissionalSaudeMapper;

    public ProfissionalSaudeController(ProfissionalSaudeService profissionalSaudeService,
                                       ProfissionalSaudeMapper profissionalSaudeMapper) {
        this.profissionalSaudeService = profissionalSaudeService;
        this.profissionalSaudeMapper = profissionalSaudeMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalSaudeResponseDTO>> listarTodos() {
        List<ProfissionalSaude> profissionais = profissionalSaudeService.listarTodos();
        List<ProfissionalSaudeResponseDTO> response = profissionais.stream()
                .map(profissionalSaudeMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalSaudeResponseDTO> buscarPorId(@PathVariable Long id) {
        return profissionalSaudeService.buscarPorId(id)
                .map(profissionalSaudeMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProfissionalSaudeResponseDTO> salvarProfissional(
            @RequestBody @Valid ProfissionalSaudeRequestDTO dto) {

        ProfissionalSaude profissional = profissionalSaudeMapper.toEntity(dto);
        ProfissionalSaude salvo = profissionalSaudeService.salvarProfissional(profissional);
        ProfissionalSaudeResponseDTO response = profissionalSaudeMapper.toResponseDTO(salvo);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProfissional(@PathVariable Long id) {
        profissionalSaudeService.excluirProfissional(id);
        return ResponseEntity.noContent().build();
    }
}
