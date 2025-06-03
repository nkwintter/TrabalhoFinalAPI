package org.serratec.trabalho.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AvaliacaoDTO {
	@NotNull(message = "ProdutoId não pode ser nulo")
    private Long produtoId;

	@Min(value = 1, message = "A nota mínima é 1.")
	@Max(value = 5, message = "A nota máxima é 5.")
    private int nota;

    @Size(max = 500, message = "Comentário pode ter até 500 caracteres")
    private String comentario;
    
    private String nomeCliente;
    
	public AvaliacaoDTO(@NotNull(message = "ProdutoId não pode ser nulo") Long produtoId,
			@Min(value = 1, message = "A nota mínima é 1.") @Max(value = 5, message = "A nota máxima é 5.") int nota,
			@Size(max = 500, message = "Comentário pode ter até 500 caracteres") String comentario) {
		super();
		this.produtoId = produtoId;
		this.nota = nota;
		this.comentario = comentario;
	}
	
	public AvaliacaoDTO() {
	}

	public Long getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}
	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
}
