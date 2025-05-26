package br.com.vidaplus.sghss.service;

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
}