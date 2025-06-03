package org.serratec.trabalho.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ListaDesejosDTO {
	
    private Long id;
    private LocalDateTime data;
    private Long clienteId;
    private List<ProdutoDTO> listadedesejos;
    
    public ListaDesejosDTO() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public List<ProdutoDTO> getListadedesejos() {
		return listadedesejos;
	}

	public void setListadedesejos(List<ProdutoDTO> listadedesejos) {
		this.listadedesejos = listadedesejos;
	}

   
}
