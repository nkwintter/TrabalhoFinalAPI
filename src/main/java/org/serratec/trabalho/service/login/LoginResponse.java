package org.serratec.trabalho.service.login;

public class LoginResponse {
	private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}