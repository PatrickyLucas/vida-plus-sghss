package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.dto.UsuarioDTO;
import br.com.vidaplus.sghss.dto.request.ProfissionalSaudeRequestDTO;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.model.Usuario;
import br.com.vidaplus.sghss.repository.ProfissionalSaudeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o serviço de profissionais de saúde.
 * Este serviço é responsável por gerenciar as operações relacionadas a profissionais de saúde,
 * incluindo listagem, busca, salvamento, exclusão e atualização de profissionais.
 *
 * @author Patricky Lucas
 */
class ProfissionalSaudeServiceTest {

    /**
     * Mocks necessários para o serviço de profissionais de saúde.
     */
    private ProfissionalSaudeRepository profissionalSaudeRepository;
    private UsuarioService usuarioService;
    private ProfissionalSaudeService profissionalSaudeService;

    /**
     * Configuração inicial dos mocks antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        profissionalSaudeRepository = mock(ProfissionalSaudeRepository.class);
        usuarioService = mock(UsuarioService.class);
        profissionalSaudeService = new ProfissionalSaudeService(profissionalSaudeRepository, usuarioService);
    }

    /**
     * Testa a listagem de todos os profissionais de saúde, verificando se retorna uma lista não vazia.
     */
    @Test
    void listarTodos_deveRetornarListaDeProfissionais() {
        ProfissionalSaude profissional = new ProfissionalSaude();
        when(profissionalSaudeRepository.findAll()).thenReturn(List.of(profissional));
        List<ProfissionalSaude> profissionais = profissionalSaudeService.listarTodos();
        assertEquals(1, profissionais.size());
    }

    /**
     * Testa a busca de um profissional de saúde por ID, verificando se retorna o profissional correto quando encontrado,
     * e vazio quando não encontrado.
     */
    @Test
    void buscarPorId_deveRetornarProfissionalQuandoEncontrado() {
        ProfissionalSaude profissional = new ProfissionalSaude();
        when(profissionalSaudeRepository.findById(1L)).thenReturn(Optional.of(profissional));
        Optional<ProfissionalSaude> resultado = profissionalSaudeService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
    }

    /**
     * Testa a busca de um profissional de saúde por ID, verificando se retorna vazio quando o profissional não é encontrado.
     */
    @Test
    void buscarPorId_deveRetornarVazioQuandoNaoEncontrado() {
        when(profissionalSaudeRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<ProfissionalSaude> resultado = profissionalSaudeService.buscarPorId(1L);
        assertFalse(resultado.isPresent());
    }

    /**
     * Testa salvamento de um profissional de saúde, verificando se o profissional é salvo corretamente.
     */
    @Test
    void salvarProfissional_deveSalvarProfissional() {
        ProfissionalSaude profissional = new ProfissionalSaude();
        when(profissionalSaudeRepository.save(profissional)).thenReturn(profissional);
        ProfissionalSaude salvo = profissionalSaudeService.salvarProfissional(profissional);
        assertEquals(profissional, salvo);
    }

    /**
     * Testa a exclusão de um profissional de saúde, verificando se o método deleteById é chamado corretamente.
     */
    @Test
    void excluirProfissional_deveExcluirProfissional() {
        profissionalSaudeService.excluirProfissional(1L);
        verify(profissionalSaudeRepository).deleteById(1L);
    }

    /**
     * Testa a atualização de um profissional de saúde, verificando se o profissional é atualizado corretamente
     * quando existe.
     */
    @Test
    void atualizarProfissionalSaude_deveAtualizarQuandoExiste() {
        ProfissionalSaude profissional = new ProfissionalSaude();
        profissional.setId(1L);
        ProfissionalSaudeRequestDTO dto = new ProfissionalSaudeRequestDTO();
        dto.setNome("Novo Nome");

        when(profissionalSaudeRepository.findById(1L)).thenReturn(Optional.of(profissional));
        when(profissionalSaudeRepository.save(any(ProfissionalSaude.class))).thenReturn(profissional);

        ProfissionalSaude atualizado = profissionalSaudeService.atualizarProfissionalSaude(1L, dto);
        assertEquals("Novo Nome", atualizado.getNome());
    }

    /**
     * Testa a atualização de um profissional de saúde, verificando se lança uma exceção quando o profissional não existe.
     */
    @Test
    void atualizarProfissionalSaude_deveLancarExcecaoQuandoNaoExiste() {
        ProfissionalSaudeRequestDTO dto = new ProfissionalSaudeRequestDTO();
        when(profissionalSaudeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> profissionalSaudeService.atualizarProfissionalSaude(1L, dto));
    }

    /**
     * Testa a criação de um profissional de saúde com usuário, verificando se o profissional é salvo corretamente.
     * Este teste simula a criação de um profissional com um usuário associado.
     */
    @Test
    void criarProfissionalComUsuario_deveCriarProfissionalComUsuario() {
        ProfissionalSaudeRequestDTO profissionalDTO = new ProfissionalSaudeRequestDTO();
        profissionalDTO.setNome("Dr. Teste");
        profissionalDTO.setEspecialidade("Cardiologia");
        profissionalDTO.setRegistroProfissional("CRM123");
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        Usuario usuario = new Usuario();
        when(usuarioService.criarUsuario(anyString(), anyString(), anyString())).thenReturn(usuario);

        ProfissionalSaude profissional = new ProfissionalSaude();
        when(profissionalSaudeRepository.save(any(ProfissionalSaude.class))).thenReturn(profissional);

        ProfissionalSaude salvo = profissionalSaudeService.criarProfissionalComUsuario(profissionalDTO, usuarioDTO);
        assertNotNull(salvo);
    }
}