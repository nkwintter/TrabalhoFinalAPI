package org.serratec.trabalho.dto;

import java.math.BigDecimal;

import org.serratec.trabalho.domain.Produto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdutoDTO {
		
	private Long id;

	@NotBlank
	private String nome;

	@NotNull
	@DecimalMin(value = "0.01", message = "Preço deve ser maior que zero.")
	private BigDecimal preco;

	public CategoriaDTO categoria;
	
	private int estoque;
	
	public ProdutoDTO(Produto produto) {
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
        	if (produto.getCategoria() != null) {
            this.categoria = new CategoriaDTO(produto.getCategoria());
        }
        this.estoque = produto.getEstoque();
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
	
	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

}
