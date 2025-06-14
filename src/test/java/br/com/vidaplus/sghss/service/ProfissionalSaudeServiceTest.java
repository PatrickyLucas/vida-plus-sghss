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

class ProfissionalSaudeServiceTest {

    private ProfissionalSaudeRepository profissionalSaudeRepository;
    private UsuarioService usuarioService;
    private ProfissionalSaudeService profissionalSaudeService;

    @BeforeEach
    void setUp() {
        profissionalSaudeRepository = mock(ProfissionalSaudeRepository.class);
        usuarioService = mock(UsuarioService.class);
        profissionalSaudeService = new ProfissionalSaudeService(profissionalSaudeRepository, usuarioService);
    }

    @Test
    void listarTodos_deveRetornarListaDeProfissionais() {
        ProfissionalSaude profissional = new ProfissionalSaude();
        when(profissionalSaudeRepository.findAll()).thenReturn(List.of(profissional));
        List<ProfissionalSaude> profissionais = profissionalSaudeService.listarTodos();
        assertEquals(1, profissionais.size());
    }

    @Test
    void buscarPorId_deveRetornarProfissionalQuandoEncontrado() {
        ProfissionalSaude profissional = new ProfissionalSaude();
        when(profissionalSaudeRepository.findById(1L)).thenReturn(Optional.of(profissional));
        Optional<ProfissionalSaude> resultado = profissionalSaudeService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
    }

    @Test
    void buscarPorId_deveRetornarVazioQuandoNaoEncontrado() {
        when(profissionalSaudeRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<ProfissionalSaude> resultado = profissionalSaudeService.buscarPorId(1L);
        assertFalse(resultado.isPresent());
    }

    @Test
    void salvarProfissional_deveSalvarProfissional() {
        ProfissionalSaude profissional = new ProfissionalSaude();
        when(profissionalSaudeRepository.save(profissional)).thenReturn(profissional);
        ProfissionalSaude salvo = profissionalSaudeService.salvarProfissional(profissional);
        assertEquals(profissional, salvo);
    }

    @Test
    void excluirProfissional_deveExcluirProfissional() {
        profissionalSaudeService.excluirProfissional(1L);
        verify(profissionalSaudeRepository).deleteById(1L);
    }

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

    @Test
    void atualizarProfissionalSaude_deveLancarExcecaoQuandoNaoExiste() {
        ProfissionalSaudeRequestDTO dto = new ProfissionalSaudeRequestDTO();
        when(profissionalSaudeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> profissionalSaudeService.atualizarProfissionalSaude(1L, dto));
    }

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