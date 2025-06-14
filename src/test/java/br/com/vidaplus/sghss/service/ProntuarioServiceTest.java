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

class ProntuarioServiceTest {

    private ProntuarioRepository prontuarioRepository;
    private ProntuarioService prontuarioService;

    @BeforeEach
    void setUp() {
        prontuarioRepository = mock(ProntuarioRepository.class);
        prontuarioService = new ProntuarioService(prontuarioRepository);
    }

    @Test
    void buscarPorPacienteId_deveRetornarProntuarioQuandoExiste() {
        Prontuario prontuario = new Prontuario();
        when(prontuarioRepository.existsById(1L)).thenReturn(true);
        when(prontuarioRepository.findById(1L)).thenReturn(Optional.of(prontuario));
        Optional<Prontuario> resultado = prontuarioService.buscarPorPacienteId(1L);
        assertTrue(resultado.isPresent());
    }

    @Test
    void buscarPorPacienteId_deveLancarExcecaoQuandoNaoExiste() {
        when(prontuarioRepository.existsById(1L)).thenReturn(false);
        assertThrows(RecursoNaoEncontradoException.class, () -> prontuarioService.buscarPorPacienteId(1L));
    }

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

    @Test
    void salvarProntuario_deveLancarExcecaoQuandoJaExiste() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        Prontuario prontuario = new Prontuario();
        prontuario.setPaciente(paciente);

        when(prontuarioRepository.findById(1L)).thenReturn(Optional.of(prontuario));
        assertThrows(ProntuarioJaExisteException.class, () -> prontuarioService.salvarProntuario(prontuario));
    }

    @Test
    void excluirProntuario_deveChamarDeleteById() {
        prontuarioService.excluirProntuario(1L);
        verify(prontuarioRepository).deleteById(1L);
    }

    @Test
    void listarTodos_deveRetornarListaDeProntuarios() {
        Prontuario prontuario = new Prontuario();
        when(prontuarioRepository.findAll()).thenReturn(List.of(prontuario));
        List<Prontuario> lista = prontuarioService.listarTodos();
        assertEquals(1, lista.size());
    }

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

    @Test
    void atualizarProntuario_deveLancarExcecaoQuandoNaoExiste() {
        when(prontuarioRepository.findById(1L)).thenReturn(Optional.empty());
        ProntuarioRequestDTO dto = new ProntuarioRequestDTO();
        assertThrows(RecursoNaoEncontradoException.class, () -> prontuarioService.atualizarProntuario(1L, dto));
    }
}