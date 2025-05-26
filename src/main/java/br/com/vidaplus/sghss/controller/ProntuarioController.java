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

@RestController
@RequestMapping("/api/prontuarios")
@PreAuthorize("hasAnyRole('MEDICO', 'ADMIN')")
public class ProntuarioController {

    private final ProntuarioService prontuarioService;
    private final PacienteService pacienteService;

    public ProntuarioController(ProntuarioService prontuarioService, PacienteService pacienteService) {
        this.prontuarioService = prontuarioService;
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<ProntuarioResponseDTO>> listarProntuarios() {
        List<Prontuario> prontuarios = prontuarioService.listarTodos();
        List<ProntuarioResponseDTO> resposta = prontuarios.stream()
                .map(ProntuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{pacienteId}")
    public ResponseEntity<ProntuarioResponseDTO> buscarPorPacienteId(@PathVariable Long pacienteId) {
        Optional<Prontuario> prontuario = prontuarioService.buscarPorPacienteId(pacienteId);
        return prontuario
                .map(ProntuarioMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProntuarioResponseDTO> salvarProntuario(@Valid @RequestBody ProntuarioRequestDTO dto) {
        Paciente paciente = pacienteService.buscarPorId(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Prontuario prontuario = ProntuarioMapper.toEntity(dto, paciente);
        Prontuario salvo = prontuarioService.salvarProntuario(prontuario);
        ProntuarioResponseDTO resposta = ProntuarioMapper.toResponseDTO(salvo);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProntuario(@PathVariable Long id) {
        prontuarioService.excluirProntuario(id);
        return ResponseEntity.noContent().build();
    }
}
