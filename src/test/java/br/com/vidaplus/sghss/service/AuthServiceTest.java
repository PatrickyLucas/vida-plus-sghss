package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.dto.response.JwtResponseDTO;
import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.model.Usuario;
import br.com.vidaplus.sghss.repository.UsuarioRepository;
import br.com.vidaplus.sghss.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private UsuarioRepository usuarioRepository;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        jwtUtil = mock(JwtUtil.class);
        userDetailsService = mock(UserDetailsService.class);
        authService = new AuthService(usuarioRepository, jwtUtil, userDetailsService);
    }

    @Test
    void registrarUsuario_deveCriptografarSenhaESalvar() {
        Usuario usuario = new Usuario();
        usuario.setPassword("senha123");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        Usuario salvo = authService.registrarUsuario(usuario);

        assertNotEquals("senha123", salvo.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches("senha123", salvo.getPassword()));
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void autenticarUsuario_deveRetornarTokenQuandoSenhaCorreta() {
        String username = "user";
        String senha = "senha123";
        String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
        UserDetails userDetails = User.withUsername(username).password(senhaCriptografada).roles("USER").build();

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("token-jwt");

        Optional<String> token = authService.autenticarUsuario(username, senha);

        assertTrue(token.isPresent());
        assertEquals("token-jwt", token.get());
    }

    @Test
    void autenticarUsuario_deveRetornarVazioQuandoSenhaIncorreta() {
        String username = "user";
        String senha = "senha123";
        String senhaErrada = "outraSenha";
        String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
        UserDetails userDetails = User.withUsername(username).password(senhaCriptografada).roles("USER").build();

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        Optional<String> token = authService.autenticarUsuario(username, senhaErrada);

        assertTrue(token.isEmpty());
    }

    @Test
    void login_deveRetornarJwtResponseQuandoSucesso() {
        String username = "user";
        String senha = "senha123";
        String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
        UserDetails userDetails = User.withUsername(username).password(senhaCriptografada).roles("USER").build();

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("token-jwt");

        JwtResponseDTO response = authService.login(username, senha);

        assertNotNull(response);
        assertEquals("token-jwt", response.getToken());
    }

    @Test
    void login_deveLancarExcecaoQuandoSenhaIncorreta() {
        String username = "user";
        String senha = "senha123";
        String senhaErrada = "outraSenha";
        String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
        UserDetails userDetails = User.withUsername(username).password(senhaCriptografada).roles("USER").build();

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        assertThrows(RecursoNaoEncontradoException.class, () -> authService.login(username, senhaErrada));
    }
}