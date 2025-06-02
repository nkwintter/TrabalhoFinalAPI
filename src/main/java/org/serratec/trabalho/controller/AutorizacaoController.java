package org.serratec.trabalho.controller;

import org.serratec.trabalho.security.JwtUtil;
import org.serratec.trabalho.security.login.LoginRequest;
import org.serratec.trabalho.security.login.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoint para login e geração de token JWT")
public class AutorizacaoController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    @Operation(
        summary = "Autenticação de usuário",
        description = "Realiza login com email e senha e retorna um token JWT para autenticação"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Login realizado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = LoginResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Email ou senha inválidos"
        )
    })
    @PostMapping
    public ResponseEntity<?> login(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Credenciais de login do usuário (email e senha)",
        required = true,
        content = @Content(schema = @Schema(implementation = LoginRequest.class))
    ) LoginRequest request) {
        try {
            Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));

            String email = auth.getName();
            String token = jwtUtil.generateToken(email);

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (AuthenticationException e) {
            return ResponseEntity
                .status(401)
                .body("Email ou senha inválidos! Confira os campos e tente novamente.");
        }
    }
}



