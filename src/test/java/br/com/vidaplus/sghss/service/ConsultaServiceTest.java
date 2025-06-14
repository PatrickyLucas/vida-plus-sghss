package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.dto.request.ConsultaRequestDTO;
import br.com.vidaplus.sghss.exception.OperacaoNaoPermitidaException;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.model.Consulta;
import br.com.vidaplus.sghss.model.Paciente;
import br.com.vidaplus.sghss.model.ProfissionalSaude;
import br.com.vidaplus.sghss.repository.ConsultaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o serviço de consultas.
 * Este serviço é responsável por gerenciar as operações relacionadas a consultas médicas,
 * incluindo listagem, busca, salvamento, exclusão e atualização de consultas.
 *
 * @author Patricky Lucas
 */
class ConsultaServiceTest {

    /**
     * Mocks necessários para o serviço de consultas.
     */
    private ConsultaRepository consultaRepository;
    private ProfissionalSaudeService profissionalSaudeService;
    private PacienteService pacienteService;
    private ConsultaService consultaService;

    /**
     * Configuração inicial dos mocks antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        consultaRepository = mock(ConsultaRepository.class);
        profissionalSaudeService = mock(ProfissionalSaudeService.class);
        pacienteService = mock(PacienteService.class);
        consultaService = new ConsultaService(consultaRepository, profissionalSaudeService);
    }

    /**
     * Testa a listagem de todas as consultas, verificando se retorna uma lista não vazia.
     */
    @Test
    void listarTodas_deveRetornarListaDeConsultas() {
        Consulta consulta = new Consulta();
        when(consultaRepository.findAll()).thenReturn(List.of(consulta));
        List<Consulta> consultas = consultaService.listarTodas();
        assertEquals(1, consultas.size());
    }

    /**
     * Testa a busca de uma consulta por ID, verificando se retorna a consulta correta quando encontrada,
     * ou um Optional vazio quando não encontrada.
     */
    @Test
    void buscarPorId_deveRetornarConsultaQuandoEncontrada() {
        Consulta consulta = new Consulta();
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        Optional<Consulta> resultado = consultaService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
    }

    /**
     * Testa a busca de uma consulta por ID, verificando se retorna um Optional vazio quando a consulta não é encontrada.
     */
    @Test
    void buscarPorId_deveRetornarVazioQuandoNaoEncontrada() {
        when(consultaRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Consulta> resultado = consultaService.buscarPorId(1L);
        assertFalse(resultado.isPresent());
    }

    /**
     * Testa o salvamento de uma consulta, verificando se a consulta é salva corretamente no repositório.
     */
    @Test
    void salvarConsulta_deveSalvarConsulta() {
        Consulta consulta = new Consulta();
        when(consultaRepository.save(consulta)).thenReturn(consulta);
        Consulta salvo = consultaService.salvarConsulta(consulta);
        assertEquals(consulta, salvo);
    }

    /**
     * Testa a exclusão de uma consulta, verificando se o método deleteById é chamado com o ID correto.
     */
    @Test
    void excluirConsulta_deveChamarDeleteById() {
        consultaService.excluirConsulta(1L);
        verify(consultaRepository).deleteById(1L);
    }

    /**
     * Testa a atualização de uma consulta, verificando se o status é atualizado corretamente quando a consulta não está concluída.
     * Também verifica se o paciente e profissional são atualizados corretamente.
     */
    @Test
    void atualizarConsulta_deveAtualizarQuandoNaoConcluida() {
        Consulta consulta = new Consulta();
        consulta.setStatus("Agendada");
        consulta.setId(1L);

        ConsultaRequestDTO dto = new ConsultaRequestDTO();
        dto.setPacienteId(2L);
        dto.setProfissionalId(3L);
        dto.setStatus("Concluída");
        dto.setData(LocalDateTime.now());

        Paciente paciente = new Paciente();
        paciente.setId(2L);
        ProfissionalSaude profissional = new ProfissionalSaude();
        profissional.setId(3L);

        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        when(pacienteService.buscarPorId(2L)).thenReturn(Optional.of(paciente));
        when(profissionalSaudeService.buscarPorId(3L)).thenReturn(Optional.of(profissional));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        Consulta atualizado = consultaService.atualizarConsulta(1L, dto, pacienteService);
        assertEquals("Concluída", atualizado.getStatus());
        assertEquals(paciente, atualizado.getPaciente());
        assertEquals(profissional, atualizado.getProfissional());
    }

    /**
     * Testa a atualização de uma consulta, verificando se lança exceção quando a consulta não é encontrada.
     */
    @Test
    void atualizarConsulta_deveLancarExcecaoQuandoConsultaNaoEncontrada() {
        ConsultaRequestDTO dto = new ConsultaRequestDTO();
        when(consultaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> consultaService.atualizarConsulta(1L, dto, pacienteService));
    }

    /**
     * Testa a atualização de uma consulta, verificando se lança exceção quando o status é "Concluída".
     */
    @Test
    void atualizarConsulta_deveLancarExcecaoQuandoStatusConcluida() {
        Consulta consulta = new Consulta();
        consulta.setStatus("Concluída");
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        ConsultaRequestDTO dto = new ConsultaRequestDTO();
        assertThrows(OperacaoNaoPermitidaException.class, () -> consultaService.atualizarConsulta(1L, dto, pacienteService));
    }

    /**
     * Testa a atualização de uma consulta, verificando se lança exceção quando o paciente não é encontrado.
     */
    @Test
    void atualizarConsulta_deveLancarExcecaoQuandoPacienteNaoEncontrado() {
        Consulta consulta = new Consulta();
        consulta.setStatus("Agendada");
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        ConsultaRequestDTO dto = new ConsultaRequestDTO();
        dto.setPacienteId(2L);
        when(pacienteService.buscarPorId(2L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> consultaService.atualizarConsulta(1L, dto, pacienteService));
    }

    /**
     * Testa a atualização de uma consulta, verificando se lança exceção quando o profissional não é encontrado.
     */
    @Test
    void atualizarConsulta_deveLancarExcecaoQuandoProfissionalNaoEncontrado() {
        Consulta consulta = new Consulta();
        consulta.setStatus("Agendada");
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        ConsultaRequestDTO dto = new ConsultaRequestDTO();
        dto.setPacienteId(2L);
        dto.setProfissionalId(3L);
        Paciente paciente = new Paciente();
        paciente.setId(2L);
        when(pacienteService.buscarPorId(2L)).thenReturn(Optional.of(paciente));
        when(profissionalSaudeService.buscarPorId(3L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> consultaService.atualizarConsulta(1L, dto, pacienteService));
    }
}