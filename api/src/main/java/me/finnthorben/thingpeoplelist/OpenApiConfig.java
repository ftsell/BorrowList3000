package me.finnthorben.thingpeoplelist;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI thingPeopleListOpenApiConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Thing-People-List Api")
                        .version("2.0.0")
                        .description("Public API of the Thing-People-List Service.\n" +
                                "Thing-People-List is a Web-Service to keep track of which people did what i.e. who " +
                                "borrowed what from you.")
                        .contact(new Contact()
                                .name("Finn-Thorben Sell")
                                .email("dev@finn-thorben.me"))
                        .license(new License().name("MIT"))
                )
                .addServersItem(new Server()
                        .description("Local server")
                        .url("/"))
                .components(new Components()
                        .addSecuritySchemes("token", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }
}
