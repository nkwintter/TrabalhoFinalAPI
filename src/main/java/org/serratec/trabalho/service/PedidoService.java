package org.serratec.trabalho.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.trabalho.domain.ItemPedido;
import org.serratec.trabalho.domain.Pedido;
import org.serratec.trabalho.domain.StatusPedido;
import org.serratec.trabalho.dto.PedidoDTO;
import org.serratec.trabalho.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	public List<PedidoDTO> buscarTodos() {
		List<Pedido> pedidoDTO = pedidoRepository.findAll();
		List<PedidoDTO> pedidosDTO = new ArrayList<>();
		for(Pedido pedido: pedidoDTO) {
			PedidoDTO usuDTO = new PedidoDTO(pedido);
			pedidosDTO.add(usuDTO);
//			new pedidoDTO(=pedido=)
		}
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
    public PedidoDTO inserir(PedidoDTO pedidoDTO) {
        Pedido pedido = toEntity(pedidoDTO);
        pedido = pedidoRepository.save(pedido);
        return new PedidoDTO(pedido);
    }

	// Deletar Pedido
    public void deleteById(Long id) {
        pedidoRepository.deleteById(id);
    }
    
    //TERMINAR( !!!
    //To entity DTO -> entidade
    private Pedido toEntity(PedidoDTO dto) {
		Pedido pedido = new Pedido();
		pedido.setId(dto.getId());
		pedido.setCliente(dto.getCliente());
		
		List<ItemPedido> itens = dto.getItens().stream().map(item -> {
			ItemPedido itnPedido =  new ItemPedido();
			//itnPedido.setPedido(pedido);
			itnPedido.setQuantidade(item.getQuantidade());
			itnPedido.setValor(item.getValorVenda());
			itnPedido.setProduto(this.produtoService.buscarId(item.getProdutoId()));
			return itnPedido;
		}).toList();
				
		pedido.setItens(itens);
		pedido.setStatus(StatusPedido.valueOf(dto.getStatus()));
		
		return pedido;
	}
}

