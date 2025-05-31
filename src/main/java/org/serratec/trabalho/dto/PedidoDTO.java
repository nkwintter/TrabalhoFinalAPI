package org.serratec.trabalho.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.serratec.trabalho.domain.Cliente;
import org.serratec.trabalho.domain.Pedido;

public class PedidoDTO {

	private Long id;
	
	private Cliente cliente;
	
	private String status;
	
	private LocalDateTime data;
	
	private List<ItemPedidoDTO> itens;
	
    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.cliente = pedido.getCliente();
        this.status = pedido.getStatus().toString();
        
        
        this.data = pedido.getDataCriacao();
    }
	
	public PedidoDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ItemPedidoDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoDTO> itens) {
		this.itens = itens;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}


}
