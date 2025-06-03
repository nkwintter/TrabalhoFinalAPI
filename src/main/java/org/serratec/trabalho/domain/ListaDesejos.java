package org.serratec.trabalho.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;

import jakarta.persistence.Entity;

@Entity
public class ListaDesejos {
	   
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @OneToOne
	    private Cliente cliente;

	    @ManyToMany
	    private List<Produto> produtos = new ArrayList<>();

	    private LocalDateTime data;

	    @PrePersist
	    public void gerarData() {
	        this.data = LocalDateTime.now();
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

		public List<Produto> getProdutos() {
			return produtos;
		}

		public void setProdutos(List<Produto> produtos) {
			this.produtos = produtos;
		}

		public LocalDateTime getDataCriacao() {
			return data;
		}

		public void setDataCriacao(LocalDateTime dataCriacao) {
			this.data = dataCriacao;
		}

	   
	}
