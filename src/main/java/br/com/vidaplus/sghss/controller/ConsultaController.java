package br.com.vidaplus.sghss.controller;

import br.com.vidaplus.sghss.dto.request.ConsultaRequestDTO;
import br.com.vidaplus.sghss.dto.response.ConsultaResponseDTO;
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

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;
    private final PacienteService pacienteService;
    private final ProfissionalSaudeService profissionalService;
    private final ConsultaMapper consultaMapper;

    public ConsultaController(ConsultaService consultaService, PacienteService pacienteService, ProfissionalSaudeService profissionalService, ConsultaMapper consultaMapper) {
        this.consultaService = consultaService;
        this.pacienteService = pacienteService;
        this.profissionalService = profissionalService;
        this.consultaMapper = consultaMapper;
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listarTodas() {
        List<Consulta> consultas = consultaService.listarTodas();
        List<ConsultaResponseDTO> resposta = consultas.stream()
                .map(consultaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarPorId(@PathVariable Long id) {
        return consultaService.buscarPorId(id)
                .map(consultaMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        consultaService.excluirConsulta(id);
        return ResponseEntity.noContent().build();
    }
}
