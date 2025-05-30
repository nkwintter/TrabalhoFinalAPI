package org.serratec.trabalho.domain;

import java.math.BigDecimal;
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

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();

	private BigDecimal totalPedido = BigDecimal.ZERO;

	private BigDecimal desconto = BigDecimal.ZERO;

	private LocalDateTime dataCriacao;

	@PrePersist
	public void setDataCriacao() {
		this.dataCriacao = LocalDateTime.now();
	}

	public void calcularTotalPedido() {
		this.totalPedido = itens.stream().map(ItemPedido::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void calcularDesconto(BigDecimal percentual) {
		if (totalPedido.compareTo(BigDecimal.ZERO) == 0) {
			calcularTotalPedido();
		}
		this.desconto = totalPedido.multiply(percentual).divide(BigDecimal.valueOf(100));
	}

	public BigDecimal getTotalComDesconto() {
		return totalPedido.subtract(this.desconto);
	}

	public Pedido() {

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

	public BigDecimal getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(BigDecimal totalPedido) {
		this.totalPedido = totalPedido;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	
	

}
