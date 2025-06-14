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

class ConsultaServiceTest {

    private ConsultaRepository consultaRepository;
    private ProfissionalSaudeService profissionalSaudeService;
    private PacienteService pacienteService;
    private ConsultaService consultaService;

    @BeforeEach
    void setUp() {
        consultaRepository = mock(ConsultaRepository.class);
        profissionalSaudeService = mock(ProfissionalSaudeService.class);
        pacienteService = mock(PacienteService.class);
        consultaService = new ConsultaService(consultaRepository, profissionalSaudeService);
    }

    @Test
    void listarTodas_deveRetornarListaDeConsultas() {
        Consulta consulta = new Consulta();
        when(consultaRepository.findAll()).thenReturn(List.of(consulta));
        List<Consulta> consultas = consultaService.listarTodas();
        assertEquals(1, consultas.size());
    }

    @Test
    void buscarPorId_deveRetornarConsultaQuandoEncontrada() {
        Consulta consulta = new Consulta();
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        Optional<Consulta> resultado = consultaService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
    }

    @Test
    void buscarPorId_deveRetornarVazioQuandoNaoEncontrada() {
        when(consultaRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Consulta> resultado = consultaService.buscarPorId(1L);
        assertFalse(resultado.isPresent());
    }

    @Test
    void salvarConsulta_deveSalvarConsulta() {
        Consulta consulta = new Consulta();
        when(consultaRepository.save(consulta)).thenReturn(consulta);
        Consulta salvo = consultaService.salvarConsulta(consulta);
        assertEquals(consulta, salvo);
    }

    @Test
    void excluirConsulta_deveChamarDeleteById() {
        consultaService.excluirConsulta(1L);
        verify(consultaRepository).deleteById(1L);
    }

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

    @Test
    void atualizarConsulta_deveLancarExcecaoQuandoConsultaNaoEncontrada() {
        ConsultaRequestDTO dto = new ConsultaRequestDTO();
        when(consultaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> consultaService.atualizarConsulta(1L, dto, pacienteService));
    }

    @Test
    void atualizarConsulta_deveLancarExcecaoQuandoStatusConcluida() {
        Consulta consulta = new Consulta();
        consulta.setStatus("Concluída");
        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));
        ConsultaRequestDTO dto = new ConsultaRequestDTO();
        assertThrows(OperacaoNaoPermitidaException.class, () -> consultaService.atualizarConsulta(1L, dto, pacienteService));
    }

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