package br.com.vidaplus.sghss.config;

import br.com.vidaplus.sghss.model.Role;
import br.com.vidaplus.sghss.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Classe de configuração para inicializar os dados do banco de dados.
 * Esta classe é responsável por criar as roles padrão no banco de dados
 * quando a aplicação é iniciada.
 *
 * @author Patricky Lucas
 */
@Configuration
public class DataInitializer {

    /**
     * Método que inicializa as roles padrão no banco de dados.
     * @param roleRepository
     * @return
     */
    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            Arrays.asList("ADMIN", "MEDICO", "PACIENTE").forEach(roleName -> {
                roleRepository.findByNome(roleName).orElseGet(() -> {
                    Role role = new Role(roleName);
                    return roleRepository.save(role);
                });
            });
        };
    }
}