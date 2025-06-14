package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.exception.UsuarioJaExisteException;
import br.com.vidaplus.sghss.model.Role;
import br.com.vidaplus.sghss.model.Usuario;
import br.com.vidaplus.sghss.repository.RoleRepository;
import br.com.vidaplus.sghss.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o serviço de usuários.
 * Este serviço é responsável por gerenciar as operações relacionadas a usuários,
 * incluindo criação, busca e verificação de existência de usuários.
 *
 * @author Patricky Lucas
 */
class UsuarioServiceTest {

    /**
     * Mocks necessários para o serviço de usuários.
     */
    private UsuarioRepository usuarioRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UsuarioService usuarioService;

    /**
     * Configuração inicial dos mocks antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        roleRepository = mock(RoleRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        usuarioService = new UsuarioService(usuarioRepository, roleRepository, passwordEncoder);
    }

    /**
     * Testa a criação de um usuário, verificando se o usuário é criado corretamente
     * quando não existe.
     */
    @Test
    void criarUsuario_deveCriarUsuarioQuandoNaoExiste() {
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("senha")).thenReturn("senhaCriptografada");
        Role role = new Role("ADMIN");
        when(roleRepository.findByNome("ADMIN")).thenReturn(Optional.of(role));
        Usuario usuarioSalvo = new Usuario("user", "senhaCriptografada");
        usuarioSalvo.setRoles(Set.of(role));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        Usuario usuario = usuarioService.criarUsuario("user", "senha", "ADMIN");

        assertEquals("user", usuario.getUsername());
        assertEquals("senhaCriptografada", usuario.getPassword());
        assertTrue(usuario.getRoles().contains(role));
    }

    /**
     * Testa a criação de um usuário, verificando se lança uma exceção quando o usuário já existe.
     */
    @Test
    void criarUsuario_deveLancarExcecaoQuandoUsuarioJaExiste() {
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(new Usuario()));

        assertThrows(UsuarioJaExisteException.class, () ->
                usuarioService.criarUsuario("user", "senha", "ADMIN"));
    }

    /**
     * Testa a criação de um usuário, verificando se lança uma exceção quando o papel (role) não existe.
     */
    @Test
    void criarUsuario_deveLancarExcecaoQuandoRoleNaoExiste() {
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.empty());
        when(roleRepository.findByNome("ADMIN")).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () ->
                usuarioService.criarUsuario("user", "senha", "ADMIN"));
    }

    /**
     * Testa a busca de um usuário por username, verificando se retorna o usuário correto quando encontrado,
     * ou um Optional vazio quando não encontrado.
     */
    @Test
    void buscarPorUsername_deveRetornarUsuarioQuandoEncontrado() {
        Usuario usuario = new Usuario("user", "senha");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorUsername("user");

        assertTrue(resultado.isPresent());
        assertEquals("user", resultado.get().getUsername());
    }

    /**
     * Testa a busca de um usuário por username, verificando se retorna vazio quando o usuário não é encontrado.
     */
    @Test
    void buscarPorUsername_deveRetornarVazioQuandoNaoEncontrado() {
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.buscarPorUsername("user");

        assertFalse(resultado.isPresent());
    }
}