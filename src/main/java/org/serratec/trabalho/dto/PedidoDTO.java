package org.serratec.trabalho.dto;

import java.util.List;

import org.serratec.trabalho.domain.Cliente;
import org.serratec.trabalho.domain.ItemPedido;
import org.serratec.trabalho.domain.Pedido;

public class PedidoDTO {

	private Long id;
	
	private Cliente cliente;
	
	private String status;
	
	private List<ItemPedidoDTO> itens;
	
    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.cliente = pedido.getCliente();
        this.status = pedido.getStatus().toString();
        this.itens = toDTO(pedido.getItens());
    }
    
    public void PedidoDTOSimplificado(Pedido pedido){
        
    }
    
    public List<ItemPedidoDTO> toDTO(List<ItemPedido> itens){
    	
    	List<ItemPedidoDTO> itensDTO = itens.stream().map(item -> {
			ItemPedidoDTO itnPedido =  new ItemPedidoDTO();
			itnPedido.setProdutoId(item.getId());
			itnPedido.setQuantidade(item.getQuantidade());
			return itnPedido;
		}).toList();
    	
    	return itensDTO;
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
}
