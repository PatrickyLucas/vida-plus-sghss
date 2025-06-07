package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.dto.request.ProntuarioRequestDTO;
import br.com.vidaplus.sghss.exception.ProntuarioJaExisteException;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.model.Prontuario;
import br.com.vidaplus.sghss.repository.ProntuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerenciar as operações relacionadas aos prontuários.
 * Inclui métodos para buscar, salvar, excluir e atualizar prontuários.
 */
@Service
public class ProntuarioService {

    /**
     * Repositório para acessar os dados dos prontuários.
     */
    private final ProntuarioRepository prontuarioRepository;

    /**
     * Construtor que recebe o repositório de prontuários.
     *
     * @param prontuarioRepository Repositório de prontuários.
     */
    public ProntuarioService(ProntuarioRepository prontuarioRepository) {
        this.prontuarioRepository = prontuarioRepository;
    }

    /**
     * Busca um prontuário pelo ID do paciente.
     *
     * @param pacienteId ID do paciente cujo prontuário será buscado.
     * @return Um Optional contendo o prontuário, se encontrado.
     */
    public Optional<Prontuario> buscarPorPacienteId(Long pacienteId) {
        // Verifica se o prontuário existe para o paciente
        if (!prontuarioRepository.existsById(pacienteId)) {
            throw new RecursoNaoEncontradoException("Prontuário não encontrado para o paciente com ID: " + pacienteId);
        }
        return prontuarioRepository.findById(pacienteId);
    }

    /**
     * Salva um novo prontuário ou atualiza um existente.
     *
     * @param prontuario Prontuário a ser salvo.
     * @return O prontuário salvo.
     */
    public Prontuario salvarProntuario(Prontuario prontuario) {
        // Verifica se já existe um prontuário para o paciente
        Optional<Prontuario> prontuarioExistente = prontuarioRepository.findById(prontuario.getPaciente().getId());
        if (prontuarioExistente.isPresent()) {
            // Se já existir, lança uma exceção
            throw new ProntuarioJaExisteException("Prontuário já existe para o paciente com ID: " + prontuario.getPaciente().getId());

        }
        // Salva o prontuário
        return prontuarioRepository.save(prontuario);
    }

    /**
     * Exclui um prontuário pelo ID.
     *
     * @param id ID do prontuário a ser excluído.
     */
    public void excluirProntuario(Long id) {
        prontuarioRepository.deleteById(id);
    }

    /**
     * Lista todos os prontuários cadastrados no sistema.
     *
     * @return Lista de prontuários.
     */
    public List<Prontuario> listarTodos() {
        return prontuarioRepository.findAll();
    }

    /**
     * Atualiza um prontuário existente, concatenando novos registros ao anterior.
     *
     * @param id  ID do prontuário a ser atualizado.
     * @param dto Dados do prontuário a serem atualizados.
     * @return O prontuário atualizado.
     */
    public Prontuario atualizarProntuario(Long id, ProntuarioRequestDTO dto) {
        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Prontuário não encontrado"));

       // Concatena o novo registro ao anterior, separando por uma linha
        String registroAnterior = prontuario.getRegistros();
        String novoRegistro = dto.getRegistros();
        prontuario.setRegistros(registroAnterior + "\n" + novoRegistro);

        return prontuarioRepository.save(prontuario);
    }

}