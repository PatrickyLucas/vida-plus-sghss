package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.dto.UsuarioDTO;
import br.com.vidaplus.sghss.dto.request.PacienteRequestDTO;
import br.com.vidaplus.sghss.dto.request.ProfissionalSaudeRequestDTO;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.model.Usuario;
import br.com.vidaplus.sghss.repository.ProfissionalSaudeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerenciar as operações relacionadas aos profissionais de saúde.
 * Inclui métodos para listar, buscar, salvar, excluir e atualizar profissionais de saúde.
 */
@Service
public class ProfissionalSaudeService {

    /**
     * Repositório para acessar os dados dos profissionais de saúde.
     */
    private final ProfissionalSaudeRepository profissionalSaudeRepository;
    private final UsuarioService usuarioService;

    /**
     * Construtor que recebe o repositório de profissionais de saúde.
     *
     * @param profissionalSaudeRepository Repositório de profissionais de saúde.
     */
    public ProfissionalSaudeService(ProfissionalSaudeRepository profissionalSaudeRepository, UsuarioService usuarioService) {
        this.profissionalSaudeRepository = profissionalSaudeRepository;
        this.usuarioService = usuarioService;
    }

    /**
     * Lista todos os profissionais de saúde cadastrados no sistema.
     *
     * @return Lista de profissionais de saúde.
     */
    public List<ProfissionalSaude> listarTodos() {
        return profissionalSaudeRepository.findAll();
    }

    /**
     * Busca um profissional de saúde pelo ID.
     *
     * @param id ID do profissional de saúde a ser buscado.
     * @return Um Optional contendo o profissional de saúde, se encontrado.
     */
    public Optional<ProfissionalSaude> buscarPorId(Long id) {
        return profissionalSaudeRepository.findById(id);
    }

    /**
     * Salva um novo profissional de saúde ou atualiza um existente.
     *
     * @param profissional Profissional de saúde a ser salvo.
     * @return O profissional de saúde salvo.
     */
    public ProfissionalSaude salvarProfissional(ProfissionalSaude profissional) {
        return profissionalSaudeRepository.save(profissional);
    }

    /**
     * Exclui um profissional de saúde pelo ID.
     *
     * @param id ID do profissional de saúde a ser excluído.
     */
    public void excluirProfissional(Long id) {
        profissionalSaudeRepository.deleteById(id);
    }

    /**
     * Atualiza os dados de um profissional de saúde existente.
     *
     * @param id ID do profissional de saúde a ser atualizado.
     * @param dto Dados do profissional de saúde a serem atualizados.
     * @return O profissional de saúde atualizado.
     */
    public ProfissionalSaude atualizarProfissionalSaude(Long id, ProfissionalSaudeRequestDTO dto) {
        ProfissionalSaude profissionalSaude = profissionalSaudeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Paciente não encontrado"));
        profissionalSaude.setNome(dto.getNome());
        return profissionalSaudeRepository.save(profissionalSaude);
    }

    @Transactional
    public ProfissionalSaude criarProfissionalComUsuario(ProfissionalSaudeRequestDTO profissionalDTO, UsuarioDTO usuarioDTO) {
        // Cria o usuário com role MEDICO
        Usuario usuario = usuarioService.criarUsuario(
                usuarioDTO.getUsername(),
                usuarioDTO.getPassword(),
                "MEDICO"
        );

        // Cria o profissional e associa o usuário
        ProfissionalSaude profissional = new ProfissionalSaude();
        profissional.setNome(profissionalDTO.getNome());
        profissional.setEspecialidade(profissionalDTO.getEspecialidade());
        profissional.setRegistroProfissional(profissionalDTO.getRegistroProfissional());
        profissional.setUsuario(usuario);

        return profissionalSaudeRepository.save(profissional);
    }
}