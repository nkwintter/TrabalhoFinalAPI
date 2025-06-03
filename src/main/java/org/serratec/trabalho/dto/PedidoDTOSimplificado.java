package org.serratec.trabalho.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.serratec.trabalho.domain.ItemPedido;
import org.serratec.trabalho.domain.Pedido;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PedidoDTOSimplificado {
	private Long id;

	private ClienteResumoDTO cliente;

	private String status;

	private List<ItemPedidoDTO> itens;
	
	private double totalSemDesconto;
	
	private int percentualDesconto = 10;

	private double totalPedido;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataHoraCriacao;

	public PedidoDTOSimplificado() {

	}

	public PedidoDTOSimplificado(Pedido pedido) {
		this.id = pedido.getId();
		this.cliente = new ClienteResumoDTO(pedido.getCliente());
		this.status = pedido.getStatus().toString();
		this.itens = toDTO(pedido.getItens());
		this.totalSemDesconto = pedido.getTotalSemDesconto();
		this.totalPedido = pedido.getTotalPedido();
		this.dataHoraCriacao = pedido.getDataCriacao();
	}

	public List<ItemPedidoDTO> toDTO(List<ItemPedido> itens) {

		List<ItemPedidoDTO> itensDTO = itens.stream().map(item -> {
			ItemPedidoDTO itnPedido = new ItemPedidoDTO();
			itnPedido.setProdutoId(item.getId());
			itnPedido.setNomeProduto(item.getProduto().getNome());
			itnPedido.setValorProduto(item.getProduto().getPreco());
			itnPedido.setQuantidade(item.getQuantidade());
			
			return itnPedido;
		}).toList();

		return itensDTO;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClienteResumoDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteResumoDTO cliente) {
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

	public double getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(double totalPedido) {
		this.totalPedido = totalPedido;
	}

	public LocalDateTime getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public double getTotalSemDesconto() {
		return totalSemDesconto;
	}

	public void setTotalSemDesconto(double totalSemDesconto) {
		this.totalSemDesconto = totalSemDesconto;
	}

	public int getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(int percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}
	
}
