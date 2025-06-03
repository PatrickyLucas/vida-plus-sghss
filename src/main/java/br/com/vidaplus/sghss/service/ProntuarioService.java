package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.dto.request.ProntuarioRequestDTO;
import br.com.vidaplus.sghss.model.Prontuario;
import br.com.vidaplus.sghss.repository.ProntuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProntuarioService {

    private final ProntuarioRepository prontuarioRepository;

    public ProntuarioService(ProntuarioRepository prontuarioRepository) {
        this.prontuarioRepository = prontuarioRepository;
    }

    public Optional<Prontuario> buscarPorPacienteId(Long pacienteId) {
        return prontuarioRepository.findById(pacienteId);
    }

    public Prontuario salvarProntuario(Prontuario prontuario) {
        return prontuarioRepository.save(prontuario);
    }

    public void excluirProntuario(Long id) {
        prontuarioRepository.deleteById(id);
    }

    // 🔥 Método para listar todos os prontuários
    public List<Prontuario> listarTodos() {
        return prontuarioRepository.findAll();
    }

    public Prontuario atualizarProntuario(Long id, ProntuarioRequestDTO dto) {
        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado"));

       // Concatena o novo registro ao anterior, separando por uma linha
        String registroAnterior = prontuario.getRegistros();
        String novoRegistro = dto.getRegistros();
        prontuario.setRegistros(registroAnterior + "\n" + novoRegistro);

        return prontuarioRepository.save(prontuario);
    }

}