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

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        roleRepository = mock(RoleRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        usuarioService = new UsuarioService(usuarioRepository, roleRepository, passwordEncoder);
    }

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

    @Test
    void criarUsuario_deveLancarExcecaoQuandoUsuarioJaExiste() {
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(new Usuario()));

        assertThrows(UsuarioJaExisteException.class, () ->
                usuarioService.criarUsuario("user", "senha", "ADMIN"));
    }

    @Test
    void criarUsuario_deveLancarExcecaoQuandoRoleNaoExiste() {
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.empty());
        when(roleRepository.findByNome("ADMIN")).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () ->
                usuarioService.criarUsuario("user", "senha", "ADMIN"));
    }

    @Test
    void buscarPorUsername_deveRetornarUsuarioQuandoEncontrado() {
        Usuario usuario = new Usuario("user", "senha");
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorUsername("user");

        assertTrue(resultado.isPresent());
        assertEquals("user", resultado.get().getUsername());
    }

    @Test
    void buscarPorUsername_deveRetornarVazioQuandoNaoEncontrado() {
        when(usuarioRepository.findByUsername("user")).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.buscarPorUsername("user");

        assertFalse(resultado.isPresent());
    }
}