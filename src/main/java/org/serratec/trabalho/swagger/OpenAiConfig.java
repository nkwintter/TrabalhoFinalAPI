package org.serratec.trabalho.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAiConfig {
	@Value("${app.dev-url}")
    private String devUrl;

    @Value("${app.prod-url}")
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
        
        License apacheLicense = new License().name("Apache  License")
        .url("http://www.apache.org/licenses/LICENSE-2.0");
        
        Info info = new Info()
                .title("🌺 API de Plantas Exóticas - Div das Plantas")
                .version("1.0")
                .description("""
                        Na **Div das Plantas** você encontra todos os tipos de flora! 🌿

                        🌸 Um cantinho verde onde cada folhinha tem seu lugar no layout!

                        _API desenvolvida com 💚 por amantes da natureza._
                        """)
                .termsOfService("https://divdasplantas.com/termos")
                .contact(contact)
                .license(apacheLicense);
        
        return new OpenAPI()
                .info(info)
                .addServersItem(devServer)
                .addServersItem(prodServer);
    }
}