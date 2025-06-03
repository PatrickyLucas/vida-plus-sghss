package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.dto.request.ConsultaRequestDTO;
import br.com.vidaplus.sghss.dto.request.PacienteRequestDTO;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.model.Consulta;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final ProfissionalSaudeService profissionalSaudeService;

    public ConsultaService(ConsultaRepository consultaRepository, ProfissionalSaudeService profissionalSaudeService) {
        this.consultaRepository = consultaRepository;
        this.profissionalSaudeService = profissionalSaudeService;
    }

    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> buscarPorId(Long id) {
        return consultaRepository.findById(id);
    }

    public Consulta salvarConsulta(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    public void excluirConsulta(Long id) {
        consultaRepository.deleteById(id);
    }

    public Consulta atualizarConsulta(Long id, ConsultaRequestDTO dto, PacienteService pacienteService) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));
        // Buscar o paciente pelo ID
        Paciente paciente = pacienteService.buscarPorId(dto.getPacienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));
        consulta.setPaciente(paciente);

        // Buscar o profissional pelo ID
        ProfissionalSaude profissional = profissionalSaudeService.buscarPorId(dto.getProfissionalId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado"));
        consulta.setProfissional(profissional);
        consulta.setStatus(dto.getStatus());
        consulta.setData(dto.getData());
        return consultaRepository.save(consulta);
    }
}