package br.com.vidaplus.sghss.service;

import br.com.vidaplus.sghss.exception.RecursoNaoEncontradoException;
import br.com.vidaplus.sghss.exception.UsuarioJaExisteException;
import br.com.vidaplus.sghss.model.Usuario;
import br.com.vidaplus.sghss.model.Role;
import br.com.vidaplus.sghss.repository.UsuarioRepository;
import br.com.vidaplus.sghss.repository.RoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

/**
 * Serviço responsável por gerenciar as operações relacionadas aos usuários.
 * Inclui métodos para criar usuários, buscar por nome de usuário e atribuir papéis.
 */
@Service
public class UsuarioService {

    /**
     * Repositório para acessar os dados dos usuários.
     */
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Construtor que recebe os repositórios de usuários e papéis, além do codificador de senhas.
     *
     * @param usuarioRepository Repositório de usuários.
     * @param roleRepository Repositório de papéis.
     * @param passwordEncoder Codificador de senhas.
     */
    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Cria um novo usuário com o nome de usuário, senha e papel especificados.
     * Verifica se o usuário já existe para evitar duplicação.
     *
     * @param username Nome de usuário do novo usuário.
     * @param password Senha do novo usuário.
     * @param roleNome Nome do papel a ser atribuído ao usuário.
     * @return O usuário criado.
     * @throws RuntimeException Se o usuário já existir ou se o papel não for encontrado.
     */
    public Usuario criarUsuario(String username, String password, String roleNome) {
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new UsuarioJaExisteException("Usuário já existe no sistema!");
        }

        String senhaCriptografada = passwordEncoder.encode(password);
        Role role = roleRepository.findByNome(roleNome).orElseThrow(() -> new RecursoNaoEncontradoException("Role não encontrada" + roleNome));

        Usuario usuario = new Usuario(username, senhaCriptografada);
        usuario.setRoles(Set.of(role));

        return usuarioRepository.save(usuario);
    }

    /**
     * Busca um usuário pelo nome de usuário.
     *
     * @param username Nome de usuário a ser buscado.
     * @return Um Optional contendo o usuário, se encontrado.
     */
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}