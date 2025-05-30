package org.serratec.trabalho.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

public class OpenAiConfig {
    @Value("http://localhost:8080")
    private String devUrl;
    @Value("http://localhost:8080")
    private String prodUrl;
    
    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Url do servidor de desenvolvimento");
        
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Url do servidor de produção");
        
        Contact contact = new Contact();
        contact.setEmail("divdasplantas@gmail.com");
        contact.setName("Div das Plantas");
        contact.setUrl("http://www.divdasplantas.org");
        
        License apacheLicense = new License().name("Apache  License")
        .url("http://www.apache.org/licenses/LICENSE-2.0");
        
        Info info = new Info().title("Api de plantas exóticas").version("1.0").contact(contact)
        .description("Na Div das Plantas você encontra todos os tipos de flora. Um cantinho verde onde cada folhinha tem seu lugar no layout!")
        .termsOfService("http://www.meudominio.com.br/termos")
        .license(apacheLicense);
        
        return new OpenAPI()
                .info(info)
                .addServersItem(devServer)
                .addServersItem(prodServer);
    }
}