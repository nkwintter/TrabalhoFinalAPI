package org.serratec.trabalho.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.trabalho.domain.ItemPedido;
import org.serratec.trabalho.domain.Pedido;
import org.serratec.trabalho.domain.Produto;
import org.serratec.trabalho.domain.StatusPedido;
import org.serratec.trabalho.dto.PedidoDTO;
import org.serratec.trabalho.repository.ClienteRepository;
import org.serratec.trabalho.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	public List<PedidoDTO> buscarTodos() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoDTO> pedidosDTO = new ArrayList<>();
		for(Pedido pedido: pedidos) {
			PedidoDTO usuDTO = new PedidoDTO(pedido);
			pedidosDTO.add(usuDTO);
//			new pedidoDTO(=pedido=)
		}
		return pedidosDTO;
	}
	
	public List<PedidoDTO> BuscarPedidosUser(String email){
		
		List<Pedido> pedidos = pedidoRepository.findAll();
		
		List<PedidoDTO> pedidosDTO = pedidos.stream()
				.filter(pedido -> pedido.getCliente() != null && pedido.getCliente().getEmail().equals(email))
				.map(PedidoDTO :: new)
				.toList();
		
		return pedidosDTO;
	}
	
	// Buscar pedido por ID
    public PedidoDTO buscar(Long id) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        if (pedidoOpt.isPresent()) {
            return new PedidoDTO(pedidoOpt.get());
        }
        return null;
    }
	
//    //REVER !!!
//	public PedidoDTO inserir(PedidoDTO pedidoInsDTO) throws EmailException{
//		
//		Pedido pedido = new Pedido();  
//		/*
//		pedido.setNome(pedidoInsDTO.getNome());
//	
//		pedido.setEmail(pedidoInsDTO.getEmail());
//		*/
//		pedido = pedidoRepository.save(pedido);
//		
//		return new PedidoDTO(pedido);
//	}
	
	// Inserir novo Pedido
//    public PedidoDTO inserir(PedidoDTO pedidoDTO) {
//        Pedido pedido = toEntity(pedidoDTO);
//        pedido = pedidoRepository.save(pedido);
//        return new PedidoDTO(pedido);
//    }
    
    //Inserir pedido por usuario
    @Transactional
    public PedidoDTO inserirPedidoUser(String email, PedidoDTO dto){
    	Pedido pedido = new Pedido();
    	pedido.setCliente(clienteRepository.findByEmail(email).get());
    	
    	List<ItemPedido> itens = dto.getItens().stream().map(item -> {
			ItemPedido itnPedido =  new ItemPedido();
			//itnPedido.setPedido(pedido); testar pra ver se funfa sem :)
			itnPedido.setQuantidade(item.getQuantidade());
			
			Produto produto = produtoService.buscarId(item.getProdutoId());
			
			itnPedido.setProduto(produto);
			return itnPedido;
		}).toList();
				
		pedido.setItens(itens);
		
		//setar o Status
		pedido.setStatus(StatusPedido.RECEBIDO);
		pedido.calcularTotalPedido();
		
		pedido = pedidoRepository.save(pedido);
        return new PedidoDTO(pedido);
    }

	// Deletar Pedido
    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }
    
    //TERMINAR( !!!
    //To entity DTO -> entidade
//    private Pedido toEntity(PedidoDTO dto) {
//		Pedido pedido = new Pedido();
//		pedido.setCliente(dto.getCliente());
//		
//		List<ItemPedido> itens = dto.getItens().stream().map(item -> {
//			ItemPedido itnPedido =  new ItemPedido();
//			//itnPedido.setPedido(pedido);
//			itnPedido.setQuantidade(item.getQuantidade());
//			itnPedido.setValor(item.getValorVenda());
//			itnPedido.setProduto(this.produtoService.buscarId(item.getProdutoId()));
//			return itnPedido;
//		}).toList();
//				
//		pedido.setItens(itens);
//		pedido.setStatus(StatusPedido.valueOf(dto.getStatus()));
//		
//		return pedido;
//	}
}

