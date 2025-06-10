package br.com.vidaplus.sghss.config;

import br.com.vidaplus.sghss.service.CustomUserDetailsService;

import br.com.vidaplus.sghss.security.JwtAuthenticationFilter;
import br.com.vidaplus.sghss.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuração de segurança da aplicação.
 * Define as regras de autenticação e autorização, além de configurar o filtro JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configura o filtro de segurança da aplicação.
     *
     * @param http               Configuração de segurança HTTP.
     * @param authProvider       Provedor de autenticação personalizado.
     * @param jwtUtil            Utilitário JWT para manipulação de tokens.
     * @param userDetailsService Serviço de detalhes do usuário.
     * @return Configuração do filtro de segurança.
     * @throws Exception Se ocorrer um erro ao configurar a segurança.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider authProvider, JwtUtil jwtUtil, UserDetailsService userDetailsService) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/pacientes/**").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/pacientes").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/pacientes/**").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/api/pacientes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/consultas/**").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/consultas").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/consultas/**").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/api/consultas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/profissionais/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/profissionais").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/profissionais/**").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/api/profissionais/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/prontuarios/**").hasAnyRole("ADMIN","MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/prontuarios").hasAnyRole("ADMIN", "MEDICO")
                        .requestMatchers(HttpMethod.GET, "/api/prontuarios/**").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/api/prontuarios/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class) // 🔥 Adicionando o filtro JWT!
                .build();
    }

    /**
     * Configura o provedor de autenticação personalizado.
     *
     * @param customUserDetailsService Serviço de detalhes do usuário personalizado.
     * @return Provedor de autenticação configurado.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Configura o gerenciador de autenticação.
     *
     * @param authenticationConfiguration Configuração de autenticação.
     * @return Gerenciador de autenticação configurado.
     * @throws Exception Se ocorrer um erro ao configurar o gerenciador de autenticação.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura o codificador de senhas.
     *
     * @return Codificador de senhas configurado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
