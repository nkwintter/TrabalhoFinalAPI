package org.serratec.trabalho.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public class EnderecoCliente {
	@NotBlank(message = "O cep deve ser preenchido!")
	@Size(min = 9, max = 9, message = "O cep deve conter 9 digitos, contando com o -.")
	private String cep;
	
	@NotBlank(message = "O logradouro deve ser preenchida!")
	private String logradouro;
	
	@NotBlank(message = "A cidade deve ser preenchida!")
	private String localidade;
	
	@NotBlank(message = "O bairro deve ser preenchida!")
	private String bairro;
	
	private String complemeto;
	
	@NotBlank(message = "O estado deve ser preenchido!")
	@Size(min = 2, max = 2, message = "A sigla do estado deve conter somente 2 letras!")
	private String uf;
	
	public EnderecoCliente() {
		
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getComplemeto() {
		return complemeto;
	}

	public void setComplemeto(String complemeto) {
		this.complemeto = complemeto;
	}
	
	
}