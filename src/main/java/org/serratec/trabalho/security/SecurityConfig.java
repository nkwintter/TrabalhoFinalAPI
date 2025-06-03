package org.serratec.trabalho.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.serratec.trabalho.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/h2-console/**").permitAll()
            	.requestMatchers("/swagger-ui/**").permitAll()
            	.requestMatchers("/notasFiscais/**").permitAll()
            	.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/clientes", "/auth").permitAll()
                .requestMatchers(HttpMethod.GET, "/clientes/me/**").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/clientes/me/**").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/produtos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/clientes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/produtos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/pedidos").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "categorias/**").permitAll()
                .requestMatchers(HttpMethod.POST, "categorias").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "categorias/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "categorias/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "avaliacoes/**").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "avaliacoes/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "avaliacoes/**").hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, "avaliacoes/**").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/categorias").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/categorias/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/pedidos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/pedidos").hasRole("USER")
                .requestMatchers(HttpMethod.PUT, "/pedidos/**").hasRole("USER")
                .requestMatchers(HttpMethod.PUT, "/pedidos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/pedidos/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //  <-> ".ALWAYS" para testes
            .headers(headers -> headers.frameOptions().sameOrigin())
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder)
            .and()
            .build();
    }

}