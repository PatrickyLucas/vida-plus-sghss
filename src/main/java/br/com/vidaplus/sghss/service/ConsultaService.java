package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.dto.request.ConsultaRequestDTO;
import br.com.vidaplus.sghss.dto.request.PacienteRequestDTO;
import br.com.vidaplus.sghss.exception.OperacaoNaoPermitidaException;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.model.Consulta;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço para gerenciar consultas médicas.
 * Permite listar, buscar, salvar, atualizar e excluir consultas.
 */
@Service
public class ConsultaService {

    /**
     * Repositório de consultas utilizado para as operações CRUD.
     */
    private final ConsultaRepository consultaRepository;
    private final ProfissionalSaudeService profissionalSaudeService;

    /**
     * Construtor do ConsultaService.
     *
     * @param consultaRepository repositório de consultas
     * @param profissionalSaudeService serviço de profissional de saúde
     */
    public ConsultaService(ConsultaRepository consultaRepository, ProfissionalSaudeService profissionalSaudeService) {
        this.consultaRepository = consultaRepository;
        this.profissionalSaudeService = profissionalSaudeService;
    }

    /**
     * Lista todas as consultas.
     *
     * @return lista de consultas
     */
    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    /**
     * Busca uma consulta pelo ID.
     *
     * @param id ID da consulta
     * @return consulta encontrada ou Optional vazio se não existir
     */
    public Optional<Consulta> buscarPorId(Long id) {
        return consultaRepository.findById(id);
    }

    /**
     * Salva uma nova consulta.
     *
     * @param consulta consulta a ser salva
     * @return consulta salva
     */
    public Consulta salvarConsulta(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    /**
     * Exclui uma consulta pelo ID.
     *
     * @param id ID da consulta a ser excluída
     */
    public void excluirConsulta(Long id) {
        consultaRepository.deleteById(id);
    }

    /**
     * Atualiza uma consulta existente.
     *
     * @param id  ID da consulta a ser atualizada
     * @param dto dados da consulta a serem atualizados
     * @param pacienteService serviço de paciente para buscar o paciente pelo ID
     * @return consulta atualizada
     */
    public Consulta atualizarConsulta(Long id, ConsultaRequestDTO dto, PacienteService pacienteService) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Consulta não encontrada"));

        // Se já está concluída, não permite alteração
        if ("Concluída".equals(consulta.getStatus())) {
            throw new OperacaoNaoPermitidaException("Não é permitido alterar uma consulta já concluída.");
        }

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