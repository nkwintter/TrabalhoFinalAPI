package org.serratec.trabalho.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ListaDesejosDTO {
	
    private Long id;
    //data da ultima atualizacao feita na lista, utlima planta add
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime data;
    @JsonIgnore
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
