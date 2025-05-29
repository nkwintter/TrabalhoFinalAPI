package org.serratec.trabalho.dto;

import java.math.BigDecimal;

import org.serratec.trabalho.domain.Produto;

import jakarta.validation.constraints.NotBlank;

public class ProdutoDTO {
		
	private Long id;

	@NotBlank
	private String nome;

	@NotBlank
	private BigDecimal preco;

	private CategoriaDTO categoria;
	
	public ProdutoDTO(Produto produto) {
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
        if (produto.getCategoria() != null) {
            this.categoria = new CategoriaDTO(produto.getCategoria());
        }
    }

	public ProdutoDTO() {

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

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

}
