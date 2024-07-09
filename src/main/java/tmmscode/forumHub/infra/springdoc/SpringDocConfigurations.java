package tmmscode.forumHub.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("Forum Hub")
                        .description("Forum Hub - Projeto desenvolvido como desafio da formação \"Oracle Next Education\" - Backend de uma aplicação responsável pelo gerenciamento de um fórum de perguntas e respostas sobre cursos, com implementação de login de usuários")
                        .contact(new Contact()
                                .name("Thiago de Melo Marçal da Silva")
                                .url("https://github.com/tmmscode"))
                        );
    }


}