package br.com.vidaplus.sghss.repository;

import br.com.vidaplus.sghss.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface para a entidade Consulta.
 * Esta interface estende JpaRepository, fornecendo métodos
 * para operações CRUD e consultas personalizadas.
 *
 * @author Patricky Lucas
 */
@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
}