package br.com.vidaplus.sghss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import br.com.vidaplus.sghss.model.Role;

/**
 * Repository interface para a entidade Role.
 * Esta interface estende JpaRepository, fornecendo métodos
 * para operações CRUD e consultas personalizadas.
 * @author Patricky Lucas
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNome(String nome);
}