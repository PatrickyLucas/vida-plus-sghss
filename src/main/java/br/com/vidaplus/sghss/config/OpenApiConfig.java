package br.com.vidaplus.sghss.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do OpenAPI para a documentação da API do Sistema de Gestão Hospitalar.
 * Define o título, versão e descrição da API, além de configurar o esquema de segurança
 * para autenticação via JWT.
 *
 * @author Patricky Lucas
 */
@Configuration
public class OpenApiConfig {

    /**
     * Método que cria a configuração do OpenAPI.
     * Define o título, versão e descrição da API, além de configurar o esquema de segurança.
     *
     * @return OpenAPI configurado
     */
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("VidaPlus SGHSS API")
                        .version("1.0")
                        .description("\n" +
                                "             API desenvolvida para o Projeto Multidisciplinar da Uninter do curso de Análise e Desenvolvimento de Sistemas,\n " +
                                "            como parte do Sistema de Gestão Hospitalar e Serviços de Saúde (SGHSS) da plataforma VidaPlus. \n" +
                                "             Fornece uma interface RESTful para operações críticas, incluindo autenticação com JWT, gerenciamento de usuários com controle de acesso baseado em papéis (RBAC), \n" +
                                "             cadastro e manutenção de entidades como pacientes, profissionais de saúde, consultas, prontuários eletrônicos e auditorias. \n" +
                                "             Projetada com foco em segurança, escalabilidade e conformidade com requisitos de sistemas hospitalares modernos.\n" +
                                "\n" +
                                "             \uD83D\uDCCC Funcionalidades principais:\n" +
                                "            - Cadastro e autenticação de usuários (roles: ADMIN, MÉDICO, PACIENTE)\n" +
                                "            - Gerenciamento de pacientes, profissionais da saúde, consultas e prontuários\n" +
                                "            - Registro de auditoria para ações críticas\n" +
                                "\n" +
                                "            \uD83D\uDD10 Segurança:\n" +
                                "            - Implementação de autenticação baseada em JWT com Spring Security\n" +
                                "            - Controle de acesso por perfil\n" +
                                "            - Conformidade com a LGPD (Lei Geral de Proteção de Dados)\n" +
                                "\n" +
                                "            ✅ Testes Automatizados:\n" +
                                "            - Foram desenvolvidos testes unitários focados na camada de serviços\n" +
                                "            - Objetivo: validar regras de negócio, comportamento esperado e tratamento de exceções.\n" +
                                "            - Tecnologias utilizadas: JUnit 5 e Mockito\n" +
                                "            - Exemplos de classes testadas:\n" +
                                "              • `PacienteService`\n" +
                                "              • `ConsultaService\n" +
                                "              • `ProntuarioService`\n" +
                                "              • `AuthService`\n" +
                                "              • `UsuarioService`\n" +
                                "              • `ProfissionalSaudeService`\n" +
                                "            - Esses testes garantem a integridade da lógica do sistema e apoiam a evolução segura do código.\n" +
                                "            \n" +
                                "            \uD83D\uDD27 Tecnologias:\n" +
                                "            Java 17+, Spring Boot, Spring Data JPA (Hibernate), Spring Security (JWT), MySQL, Lombok, Swagger")
                        .contact(new Contact()
                            .name("Patricky Lucas")
                            .email("patrickylucas@hotmail.com")
                            .url("https://github.com/PatrickyLucas/vida-plus-sghss"))
                        .license(new License()
                            .name("MIT License")
                            .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}