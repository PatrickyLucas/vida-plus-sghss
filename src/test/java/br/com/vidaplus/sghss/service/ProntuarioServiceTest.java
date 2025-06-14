package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.dto.request.ProntuarioRequestDTO;
import br.com.vidaplus.sghss.exception.ProntuarioJaExisteException;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.model.Prontuario;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.repository.ProntuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o serviço de prontuários.
 * Este serviço é responsável por gerenciar as operações relacionadas a prontuários médicos,
 * incluindo busca, salvamento, exclusão, listagem e atualização de prontuários.
 *
 * @author Patricky Lucas
 */
class ProntuarioServiceTest {

    /**
     * Mocks necessários para o serviço de prontuários.
     */
    private ProntuarioRepository prontuarioRepository;
    private ProntuarioService prontuarioService;

    /**
     * Configuração inicial dos mocks antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        prontuarioRepository = mock(ProntuarioRepository.class);
        prontuarioService = new ProntuarioService(prontuarioRepository);
    }

    /**
     * Testa a busca de um prontuário por ID, verificando se retorna o prontuário correto quando encontrado,
     * e vazio quando não encontrado.
     */
    @Test
    void buscarPorPacienteId_deveRetornarProntuarioQuandoExiste() {
        Prontuario prontuario = new Prontuario();
        when(prontuarioRepository.existsById(1L)).thenReturn(true);
        when(prontuarioRepository.findById(1L)).thenReturn(Optional.of(prontuario));
        Optional<Prontuario> resultado = prontuarioService.buscarPorPacienteId(1L);
        assertTrue(resultado.isPresent());
    }

    /**
     * Testa a busca de um prontuário por ID, verificando se lança uma exceção quando o prontuário não existe.
     */
    @Test
    void buscarPorPacienteId_deveLancarExcecaoQuandoNaoExiste() {
        when(prontuarioRepository.existsById(1L)).thenReturn(false);
        assertThrows(RecursoNaoEncontradoException.class, () -> prontuarioService.buscarPorPacienteId(1L));
    }

    /**
     * Testa salvamento de um prontuário, verificando se salva corretamente quando não existe,
     */
    @Test
    void salvarProntuario_deveSalvarQuandoNaoExiste() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        Prontuario prontuario = new Prontuario();
        prontuario.setPaciente(paciente);

        when(prontuarioRepository.findById(1L)).thenReturn(Optional.empty());
        when(prontuarioRepository.save(prontuario)).thenReturn(prontuario);

        Prontuario salvo = prontuarioService.salvarProntuario(prontuario);
        assertEquals(prontuario, salvo);
    }

    /**
     * Testa o salvamento de um prontuário, verificando se lança uma exceção quando já existe.
     */
    @Test
    void salvarProntuario_deveLancarExcecaoQuandoJaExiste() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        Prontuario prontuario = new Prontuario();
        prontuario.setPaciente(paciente);

        when(prontuarioRepository.findById(1L)).thenReturn(Optional.of(prontuario));
        assertThrows(ProntuarioJaExisteException.class, () -> prontuarioService.salvarProntuario(prontuario));
    }

    /**
     * Testa a exclusão de um prontuário, verificando se o método deleteById é chamado corretamente.
     */
    @Test
    void excluirProntuario_deveChamarDeleteById() {
        prontuarioService.excluirProntuario(1L);
        verify(prontuarioRepository).deleteById(1L);
    }

    /**
     * Testa a listagem de todos os prontuários, verificando se retorna uma lista não vazia.
     */
    @Test
    void listarTodos_deveRetornarListaDeProntuarios() {
        Prontuario prontuario = new Prontuario();
        when(prontuarioRepository.findAll()).thenReturn(List.of(prontuario));
        List<Prontuario> lista = prontuarioService.listarTodos();
        assertEquals(1, lista.size());
    }

    /**
     * Testa a atualização de um prontuário, verificando se o prontuário é atualizado corretamente
     * quando existe.
     */
    @Test
    void atualizarProntuario_deveConcatenarRegistros() {
        Prontuario prontuario = new Prontuario();
        prontuario.setRegistros("Registro antigo");
        when(prontuarioRepository.findById(1L)).thenReturn(Optional.of(prontuario));
        when(prontuarioRepository.save(any(Prontuario.class))).thenReturn(prontuario);

        ProntuarioRequestDTO dto = new ProntuarioRequestDTO();
        dto.setRegistros("Novo registro");

        Prontuario atualizado = prontuarioService.atualizarProntuario(1L, dto);
        assertTrue(atualizado.getRegistros().contains("Registro antigo"));
        assertTrue(atualizado.getRegistros().contains("Novo registro"));
    }

    /**
     * Testa a atualização de um prontuário, verificando se lança uma exceção quando não existe.
     */
    @Test
    void atualizarProntuario_deveLancarExcecaoQuandoNaoExiste() {
        when(prontuarioRepository.findById(1L)).thenReturn(Optional.empty());
        ProntuarioRequestDTO dto = new ProntuarioRequestDTO();
        assertThrows(RecursoNaoEncontradoException.class, () -> prontuarioService.atualizarProntuario(1L, dto));
    }
}