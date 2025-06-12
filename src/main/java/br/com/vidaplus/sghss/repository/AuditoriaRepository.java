package br.com.vidaplus.sghss.repository;

import br.com.vidaplus.sghss.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    // Método para buscar auditorias por nome de usuário
    List<Auditoria> findByUsuario(String userName);
}