package org.serratec.trabalho.dto;

import org.hibernate.validator.constraints.br.CPF;
import org.serratec.trabalho.domain.Cliente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClienteDTO {

	private Long id;

	@NotBlank(message = "O nome é obrigatório.")
	private String nome;

	@NotBlank(message = "O telefone é obrigatório.")
	private String telefone;
	
	private String role;
	
	@Email(message = "E-mail inválido.")
	@NotBlank(message = "O e-mail é obrigatório.")
	private String email;
	
	@NotBlank(message = "A senha é obrigatória.")
    private String senha;

	@CPF(message = "CPF inválido.")
	@NotBlank(message = "O CPF é obrigatório.")
	private String cpf;

	@NotNull(message = "O endereço é obrigatório")
	@Valid
	private EnderecoDTO endereco;

	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail();
		this.cpf = cliente.getCpf();
		this.senha = cliente.getSenha();
		this.role = cliente.getRole();
		if (cliente.getEndereco() != null) {
			this.endereco = new EnderecoDTO(cliente.getEndereco());
		}
	}

	public ClienteDTO() {

	}

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

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

}