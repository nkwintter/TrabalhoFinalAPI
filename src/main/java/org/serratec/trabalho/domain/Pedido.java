package org.serratec.trabalho.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	private double totalSemDesconto;

	private double totalPedido;

	private int desconto = 10;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataCriacao;

	@PrePersist
	public void setDataCriacao() {
		this.dataCriacao = LocalDateTime.now();
	}

	public void calcularTotalPedido() {
		double subTotal = 0;
		
		for(ItemPedido item : this.itens){
			subTotal += item.getSubtotal();
		}
		
		this.totalSemDesconto = subTotal;
				
		this.totalPedido = subTotal - (subTotal * ((double)this.desconto / 100));
		System.out.println(totalPedido);
		
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

	public double getTotalSemDesconto() {
		return totalSemDesconto;
	}

	public void setTotalSemDesconto(double totalSemDesconto) {
		this.totalSemDesconto = totalSemDesconto;
	}
	
	
}
