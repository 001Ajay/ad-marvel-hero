package com.dev.ad_marvel_hero.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${app.localhost-url}")
    private String localHostUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/api-docs/**", "/swagger-ui/**").permitAll()
                        .anyRequest().permitAll())
                .build();
    }

    @Bean
    public OpenAPI myApis(){
        Server localhost = new Server();
        localhost.setUrl(localHostUrl);
        localhost.setDescription("Local Server");

        Contact contact = new Contact();
        contact.setEmail("ajaydas5478@gmail");
        contact.setName("Ajay Das");
        contact.setUrl("https://github.com/001Ajay");

        License mitLicense = new License()
                .name("MIT License")
                .url("http://www.apache.org/licenses/LICENSE-2.0.html");

        Info info = new Info()
                .title("Marvel Comics Hero APIs")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to profile data from Marvel Comics service.")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localhost));
    }


}
