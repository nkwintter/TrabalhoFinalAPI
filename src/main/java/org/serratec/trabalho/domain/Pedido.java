package org.serratec.trabalho.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Cliente cliente;

	@Enumerated(EnumType.STRING)
	private StatusPedido status;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "pedido_id")
	private List<ItemPedido> itens = new ArrayList<>();

	private Double totalPedido;

	private int desconto = 10;

	private LocalDateTime dataCriacao;

	@PrePersist
	public void setDataCriacao() {
		this.dataCriacao = LocalDateTime.now();
	}

	public void calcularTotalPedido() {
		
		double subTotal = itens.stream()
				.mapToDouble((ItemPedido::getSubtotal))
				.sum();
				
		this.totalPedido = subTotal - (subTotal * (desconto / 100));
		
	}

	public Pedido() {
		setDataCriacao();
	}

	public Pedido(Long id, Cliente cliente, StatusPedido status, List<ItemPedido> itens) {
		this.id = id;
		this.cliente = cliente;
		this.status = status;
		this.itens = itens;
		setDataCriacao();
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

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public Double getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(Double totalPedido) {
		this.totalPedido = totalPedido;
	}

	public int getDesconto() {
		return desconto;
	}

	public void setDesconto(int desconto) {
		this.desconto = desconto;
	}
	
	
}
