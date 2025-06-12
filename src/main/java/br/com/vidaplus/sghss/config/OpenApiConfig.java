package br.com.vidaplus.sghss.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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
                        .description("Documentação da API do Sistema de Gestão Hospitalar e Serviços de Saúde (SGHSS) do VidaPlus"))
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