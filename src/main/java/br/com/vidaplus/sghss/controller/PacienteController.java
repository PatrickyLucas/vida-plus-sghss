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

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarTodos() {
        List<Paciente> pacientes = pacienteService.listarTodos();
        List<PacienteResponseDTO> dtos = pacientes.stream()
                .map(PacienteMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return pacienteService.buscarPorId(id)
                .map(paciente -> ResponseEntity.ok(PacienteMapper.toResponseDTO(paciente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> salvarPaciente(@Valid @RequestBody PacienteRequestDTO requestDTO) {
        Paciente paciente = PacienteMapper.toEntity(requestDTO);
        Paciente salvo = pacienteService.salvarPaciente(paciente);
        return ResponseEntity.ok(PacienteMapper.toResponseDTO(salvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPaciente(@PathVariable Long id) {
        pacienteService.excluirPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
