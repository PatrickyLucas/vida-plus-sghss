package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.dto.UsuarioDTO;
import br.com.vidaplus.sghss.dto.request.PacienteRequestDTO;
import br.com.vidaplus.sghss.exception.CpfJaCadastradoException;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.Usuario;
import br.com.vidaplus.sghss.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o serviço de pacientes.
 * Este serviço é responsável por gerenciar as operações relacionadas a pacientes,
 * incluindo listagem, busca, salvamento, exclusão e atualização de pacientes.
 *
 * @author Patricky Lucas
 */
class PacienteServiceTest {

    /**
     * Mocks necessários para o serviço de pacientes.
     */
    private PacienteRepository pacienteRepository;
    private UsuarioService usuarioService;
    private PacienteService pacienteService;

    /**
     * Configuração inicial dos mocks antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        pacienteRepository = mock(PacienteRepository.class);
        usuarioService = mock(UsuarioService.class);
        pacienteService = new PacienteService(pacienteRepository, usuarioService);
    }

    /**
     * Testa a listagem de todos os pacientes, verificando se retorna uma lista não vazia.
     */
    @Test
    void listarTodos_deveRetornarListaDePacientes() {
        Paciente paciente = new Paciente();
        when(pacienteRepository.findAll()).thenReturn(List.of(paciente));
        List<Paciente> pacientes = pacienteService.listarTodos();
        assertEquals(1, pacientes.size());
    }

    /**
     * Testa a busca de um paciente por ID, verificando se retorna o paciente correto quando encontrado,
     * e vazio quando não encontrado.
     */
    @Test
    void buscarPorId_deveRetornarPacienteQuandoEncontrado() {
        Paciente paciente = new Paciente();
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        Optional<Paciente> resultado = pacienteService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
    }

    /**
     * Testa a busca de um paciente por ID, verificando se retorna vazio quando o paciente não é encontrado.
     */
    @Test
    void buscarPorId_deveRetornarVazioQuandoNaoEncontrado() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Paciente> resultado = pacienteService.buscarPorId(1L);
        assertFalse(resultado.isPresent());
    }

    /**
     * Testa salvar um paciente, verificando se o paciente é salvo corretamente
     * quando o CPF não existe.
     */
    @Test
    void salvarPaciente_deveSalvarQuandoCpfNaoExiste() {
        Paciente paciente = new Paciente();
        paciente.setCpf("12345678900");
        when(pacienteRepository.findByCpf("12345678900")).thenReturn(Optional.empty());
        when(pacienteRepository.save(paciente)).thenReturn(paciente);
        Paciente salvo = pacienteService.salvarPaciente(paciente);
        assertEquals(paciente, salvo);
    }

    /**
     * Testa salvar um paciente, verificando se lança uma exceção quando o CPF já existe.
     */
    @Test
    void salvarPaciente_deveLancarExcecaoQuandoCpfJaExiste() {
        Paciente paciente = new Paciente();
        paciente.setCpf("12345678900");
        when(pacienteRepository.findByCpf("12345678900")).thenReturn(Optional.of(paciente));
        assertThrows(CpfJaCadastradoException.class, () -> pacienteService.salvarPaciente(paciente));
    }

    /**
     * Testa a exclusão de um paciente, verificando se o método deleteById é chamado corretamente
     * quando o paciente existe.
     */
    @Test
    void excluirPaciente_deveExcluirQuandoExiste() {
        when(pacienteRepository.existsById(1L)).thenReturn(true);
        pacienteService.excluirPaciente(1L);
        verify(pacienteRepository).deleteById(1L);
    }

    /**
     * Testa a exclusão de um paciente, verificando se lança uma exceção quando o paciente não existe.
     */
    @Test
    void excluirPaciente_deveLancarExcecaoQuandoNaoExiste() {
        when(pacienteRepository.existsById(1L)).thenReturn(false);
        assertThrows(RecursoNaoEncontradoException.class, () -> pacienteService.excluirPaciente(1L));
    }

    /**
     * Testa a atualização de um paciente, verificando se o paciente é atualizado corretamente
     * quando existe.
     */
    @Test
    void atualizarPaciente_deveAtualizarQuandoExiste() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        PacienteRequestDTO dto = new PacienteRequestDTO();
        dto.setNome("Novo Nome");
        dto.setDataNascimento(LocalDate.now());
        dto.setHistoricoClinico("Histórico");

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente atualizado = pacienteService.atualizarPaciente(1L, dto);
        assertEquals("Novo Nome", atualizado.getNome());
    }

    /**
     * Testa a atualização de um paciente, verificando se lança uma exceção quando o paciente não existe.
     */
    @Test
    void atualizarPaciente_deveLancarExcecaoQuandoNaoExiste() {
        PacienteRequestDTO dto = new PacienteRequestDTO();
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> pacienteService.atualizarPaciente(1L, dto));
    }

    /**
     * Testa a criação de um paciente com usuário, verificando se o paciente é criado corretamente
     * quando o CPF não existe.
     */
    @Test
    void criarPacienteComUsuario_deveCriarQuandoCpfNaoExiste() {
        PacienteRequestDTO pacienteDTO = new PacienteRequestDTO();
        pacienteDTO.setCpf("12345678900");
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        when(pacienteRepository.findByCpf("12345678900")).thenReturn(Optional.empty());
        Usuario usuario = new Usuario();
        when(usuarioService.criarUsuario(anyString(), anyString(), anyString())).thenReturn(usuario);
        Paciente paciente = new Paciente();
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente salvo = pacienteService.criarPacienteComUsuario(pacienteDTO, usuarioDTO);
        assertNotNull(salvo);
    }

    /**
     * Testa a criação de um paciente com usuário, verificando se lança uma exceção quando o CPF já existe.
     */
    @Test
    void criarPacienteComUsuario_deveLancarExcecaoQuandoCpfJaExiste() {
        PacienteRequestDTO pacienteDTO = new PacienteRequestDTO();
        pacienteDTO.setCpf("12345678900");
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        when(pacienteRepository.findByCpf("12345678900")).thenReturn(Optional.of(new Paciente()));
        assertThrows(CpfJaCadastradoException.class, () -> pacienteService.criarPacienteComUsuario(pacienteDTO, usuarioDTO));
    }
}