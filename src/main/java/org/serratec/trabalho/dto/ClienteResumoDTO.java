package org.serratec.trabalho.dto;

import org.serratec.trabalho.domain.Cliente;

public class ClienteResumoDTO {

	private String nome;
	private String cpf;

	public ClienteResumoDTO() {
	}

	public ClienteResumoDTO(Cliente cliente) {
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}
}


