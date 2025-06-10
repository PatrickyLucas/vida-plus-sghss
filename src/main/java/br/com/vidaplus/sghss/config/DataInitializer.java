package br.com.vidaplus.sghss.config;

import br.com.vidaplus.sghss.model.Role;
import br.com.vidaplus.sghss.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

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