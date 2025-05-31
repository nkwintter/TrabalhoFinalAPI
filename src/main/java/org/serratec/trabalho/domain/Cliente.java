package org.serratec.trabalho.domain;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
        @Size(min = 2, max = 100)
    @NotBlank(message = "O nome é obrigatório.")
	@Pattern(regexp = "^[A-Za-zÀ-ÿ\\s'-]+$", message = "O nome não pode conter números ou caracteres especiais.")
    private String nome;
   
    @NotBlank(message = "O telefone é obrigatório.")
    @Size(min = 10, max = 15, message = "O telefone deve ter entre 10 e 15 caracteres.")
    @Pattern(regexp = "^[0-9+()\\-\\s]*$", message = "Telefone inválido. Por favor, verifique o número informado.")
    private String telefone;
    
	@Email(message = "E-mail inválido.")
	@NotBlank(message = "O e-mail é obrigatório.") 
    private String email;
    
	@CPF(message = "CPF inválido.")
	@NotBlank(message = "O CPF é obrigatório.") 
    private String cpf;
    
    @NotNull(message = "O endereço é obrigatório.")
	@Embedded
	@Valid
	private EnderecoCliente endereco;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public EnderecoCliente getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoCliente endereco) {
		this.endereco = endereco;
	}
}
