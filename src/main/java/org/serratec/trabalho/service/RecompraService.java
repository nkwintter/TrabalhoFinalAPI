package org.serratec.trabalho.service;


import java.util.ArrayList;
import java.util.List;

import org.serratec.trabalho.domain.Cliente;
import org.serratec.trabalho.domain.ItemPedido;
import org.serratec.trabalho.domain.Pedido;
import org.serratec.trabalho.domain.Produto;
import org.serratec.trabalho.domain.StatusPedido;
import org.serratec.trabalho.dto.PedidoDTO;
import org.serratec.trabalho.repository.ClienteRepository;
import org.serratec.trabalho.repository.PedidoRepository;
import org.serratec.trabalho.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecompraService {
	
	private final PedidoRepository pedidoRepository;
	private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    
    
    @Autowired
    public RecompraService(PedidoRepository pedidoRepository,
    					   ClienteRepository clienteRepository,
    					   ProdutoRepository produtoRepository) {
    	this.pedidoRepository = pedidoRepository;
    	this.clienteRepository = clienteRepository;
    	this.produtoRepository =  produtoRepository;
    }
    
    public PedidoDTO recompraUltimoPedido(Long clienteId) {
    	
    	Cliente cliente = clienteRepository.findById(clienteId)
    			.orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    
    	Pedido ultimoPedido = pedidoRepository.findTopByClienteOrderByDataCriacaoDesc(cliente)
    			.orElseThrow(() -> new RuntimeException("Nenhum pedido anterior encontrado"));
    	
    	Pedido novoPedido = new Pedido();
    	novoPedido.setCliente(cliente);
    	novoPedido.setDataCriacao();
    
    	
    	List<ItemPedido> novosItens = new ArrayList<>();
    	for (ItemPedido itemAntigo : ultimoPedido.getItens()) {
    		Produto produto = produtoRepository.findById(itemAntigo.getProduto().getId())
    				.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    		
    		ItemPedido novoItem = new ItemPedido();
    		novoItem.setProduto(produto);
    		novoItem.setQuantidade(itemAntigo.getQuantidade());
    		novoItem.setValor(produto.getPreco());
    		novoItem.setPedido(novoPedido);
    		
    		novosItens.add(novoItem);
    	}
    	
    	novoPedido.setItens(novosItens);
    	novoPedido.setStatus(StatusPedido.RECEBIDO);
    	
    	Pedido pedidoSalvo = pedidoRepository.save(novoPedido);
    	return new PedidoDTO(pedidoSalvo);
    
    
    }
    
	

}
