package org.serratec.trabalho.dto;

import org.serratec.trabalho.domain.Produto;
import jakarta.validation.constraints.NotNull;

public class ProdutoDTO {
		
	private Long id;

	
	private String nome;

	@NotNull
	private double preco;
	
	private int estoque;
	
	private CategoriaDTO categoria;
	
	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
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

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
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
