package br.com.vidaplus.sghss.repository;

import br.com.vidaplus.sghss.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface para a entidade Usuario.
 * Esta interface estende JpaRepository, fornecendo métodos
 * para operações CRUD e consultas personalizadas.
 * @author Patricky Lucas
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}