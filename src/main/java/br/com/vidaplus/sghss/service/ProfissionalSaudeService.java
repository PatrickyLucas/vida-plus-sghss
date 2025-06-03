package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.dto.request.PacienteRequestDTO;
import br.com.vidaplus.sghss.dto.request.ProfissionalSaudeRequestDTO;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.repository.ProfissionalSaudeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalSaudeService {

    private final ProfissionalSaudeRepository profissionalSaudeRepository;

    public ProfissionalSaudeService(ProfissionalSaudeRepository profissionalSaudeRepository) {
        this.profissionalSaudeRepository = profissionalSaudeRepository;
    }

    public List<ProfissionalSaude> listarTodos() {
        return profissionalSaudeRepository.findAll();
    }

    public Optional<ProfissionalSaude> buscarPorId(Long id) {
        return profissionalSaudeRepository.findById(id);
    }

    public ProfissionalSaude salvarProfissional(ProfissionalSaude profissional) {
        return profissionalSaudeRepository.save(profissional);
    }

    public void excluirProfissional(Long id) {
        profissionalSaudeRepository.deleteById(id);
    }

    public ProfissionalSaude atualizarProfissionalSaude(Long id, ProfissionalSaudeRequestDTO dto) {
        ProfissionalSaude profissionalSaude = profissionalSaudeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));
        profissionalSaude.setNome(dto.getNome());
        return profissionalSaudeRepository.save(profissionalSaude);
    }
}