package br.com.vidaplus.sghss.repository;

import br.com.vidaplus.sghss.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface para a entidade Auditoria.
 * Esta interface estende JpaRepository, fornecendo métodos
 * para operações CRUD e consultas personalizadas.
 * @author Patricky Lucas
 */
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    // Método para buscar auditorias por nome de usuário
    List<Auditoria> findByUsuario(String userName);
}