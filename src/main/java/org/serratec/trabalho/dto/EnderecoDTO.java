package org.serratec.trabalho.dto;

import org.serratec.trabalho.domain.EnderecoCliente;

import jakarta.validation.constraints.NotBlank;

public class EnderecoDTO {
	
	@NotBlank(message = "O CEP é obrigatório.")
	private String cep;
	
	private String logradouro;
	private String complemento;
	private String localidade;
	private String bairro;
	private String uf;
	
	public EnderecoDTO(){
		
	}
	
	public EnderecoDTO(EnderecoCliente endereco) {
        this.cep = endereco.getCep();
        this.logradouro = endereco.getLogradouro();
        this.localidade = endereco.getLocalidade();
        this.bairro = endereco.getBairro();
        this.uf = endereco.getUf();
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
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
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
		
}
