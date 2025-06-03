package org.serratec.trabalho.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.trabalho.domain.Cliente;
import org.serratec.trabalho.domain.ItemPedido;
import org.serratec.trabalho.domain.Pedido;
import org.serratec.trabalho.domain.Produto;
import org.serratec.trabalho.domain.StatusPedido;
import org.serratec.trabalho.dto.PedidoDTO;
import org.serratec.trabalho.dto.PedidoDTOSimplificado;
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
	
	@Autowired
	private NotaFiscalService notaFiscalService;
	
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
    
    //Post pedido por usuario
    @Transactional
    public PedidoDTOSimplificado inserirPedidoUser(String email, PedidoDTO dto){
    	Pedido pedido = new Pedido();
    	pedido.setCliente(clienteRepository.findByEmail(email).get());
    	
    	List<ItemPedido> itens = dto.getItens().stream().map(item -> {
			ItemPedido itnPedido =  new ItemPedido();
			Produto produto = produtoService.buscarId(item.getProdutoId());
			
			itnPedido.setProduto(produto);
			
			itnPedido.setQuantidade(item.getQuantidade());
			itnPedido.setValor(produto.getPreco());
			
			return itnPedido;
		}).toList();
				
		pedido.setItens(itens);
		
		//setar o Status
		pedido.setStatus(StatusPedido.RECEBIDO);
		
		pedido.calcularTotalPedido();
		
		pedido = pedidoRepository.save(pedido);
		
		//gera a nota fiscal do pedido do cliente
		PedidoDTOSimplificado pedidoSimplificado = new PedidoDTOSimplificado(pedido);
		String notaFiscal = notaFiscalService.gerarNotaFiscal(pedidoSimplificado);
		pedidoSimplificado.setNotaFiscal(notaFiscal);
		
        return pedidoSimplificado;
    }
    
    	public PedidoDTOSimplificado updateByEmail(String email, PedidoDTO dto, Long id) {
    		Cliente cliente = clienteRepository.findByEmail(email).get();
    		
    		Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
    		
    		if(cliente.getRole().equals("ADMIN")) {
    			Pedido pedido = pedidoOpt.get();
    			pedido.setStatus(StatusPedido.valueOf(dto.getStatus().toUpperCase()));
    			
    			pedido = pedidoRepository.save(pedido);
    			return new PedidoDTOSimplificado(pedido);
    		}
    		
    		//caso seja User, REVER!
    		return null;
   
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

