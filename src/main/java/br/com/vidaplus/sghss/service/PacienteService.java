package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.dto.request.PacienteRequestDTO;
import br.com.vidaplus.sghss.exception.CpfJaCadastradoException;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerenciar as operações relacionadas aos pacientes.
 * Inclui métodos para listar, buscar, salvar, excluir e atualizar pacientes.
 */
@Service
public class PacienteService {

    /**
     * Repositório para acessar os dados dos pacientes.
     */
    private final PacienteRepository pacienteRepository;

    /**
     * Construtor que recebe o repositório de pacientes.
     *
     * @param pacienteRepository Repositório de pacientes.
     */
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Lista todos os pacientes cadastrados no sistema.
     *
     * @return Lista de pacientes.
     */
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    /**
     * Busca um paciente pelo ID.
     *
     * @param id ID do paciente a ser buscado.
     * @return Um Optional contendo o paciente, se encontrado.
     */
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    /**
     * Salva um novo paciente ou atualiza um existente.
     *
     * @param paciente Paciente a ser salvo.
     * @return O paciente salvo.
     */
    public Paciente salvarPaciente(Paciente paciente) {
        if (pacienteRepository.findByCpf(paciente.getCpf()).isPresent()) {
            throw new CpfJaCadastradoException("CPF já cadastrado no sistema!");
        }
        return pacienteRepository.save(paciente);
    }

    /**
     * Exclui um paciente pelo ID.
     *
     * @param id ID do paciente a ser excluído.
     */
    public void excluirPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Paciente não encontrado");
        }
        pacienteRepository.deleteById(id);
    }

    /**
     * Atualiza os dados de um paciente existente.
     *
     * @param id ID do paciente a ser atualizado.
     * @param dto Dados do paciente a serem atualizados.
     * @return O paciente atualizado.
     */
    public Paciente atualizarPaciente(Long id, PacienteRequestDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));
        paciente.setNome(dto.getNome());
        paciente.setDataNascimento(dto.getDataNascimento());
        paciente.setHistoricoClinico(dto.getHistoricoClinico());
        return pacienteRepository.save(paciente);
    }
}