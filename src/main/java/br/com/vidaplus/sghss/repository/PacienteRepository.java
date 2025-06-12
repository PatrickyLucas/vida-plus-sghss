package br.com.vidaplus.sghss.repository;

import br.com.vidaplus.sghss.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface para a entidade Paciente.
 * Esta interface estende JpaRepository, fornecendo métodos
 * para operações CRUD e consultas personalizadas.
 * @author Patricky Lucas
 */
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCpf(String cpf);
}