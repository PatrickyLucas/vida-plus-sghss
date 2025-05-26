package br.com.vidaplus.sghss.service;

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

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔥 Método para criar um usuário com papel atribuído
    public Usuario criarUsuario(String username, String password, String roleNome) {
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Usuário já existe no sistema!"); // 🔥 Evita duplicação!
        }

        String senhaCriptografada = passwordEncoder.encode(password);
        Role role = roleRepository.findByNome(roleNome).orElseThrow(() -> new RuntimeException("Role não encontrada" + roleNome));

        Usuario usuario = new Usuario(username, senhaCriptografada);
        usuario.setRoles(Set.of(role));

        return usuarioRepository.save(usuario);
    }


    // 🔍 Método para buscar usuário pelo nome
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}