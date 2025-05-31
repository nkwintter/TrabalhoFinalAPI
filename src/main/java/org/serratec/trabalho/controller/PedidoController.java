package org.serratec.trabalho.controller;

import java.util.List;


import org.serratec.trabalho.dto.PedidoDTO;
import org.serratec.trabalho.service.PedidoService;
import org.serratec.trabalho.service.RecompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Endpoints para pedidos e recompra")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private RecompraService recompraService;
	
	
	
	
	
	
	@GetMapping
	@Operation(summary = "Listar todos os pedidos")
	public ResponseEntity<List<PedidoDTO>> listar(){
		return ResponseEntity.ok(pedidoService.buscarTodos());
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Listar um pedido especifico")
	public ResponseEntity<PedidoDTO> buscar(@PathVariable Long id) {
		PedidoDTO dto = pedidoService.buscar(id);
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	@Operation(summary = "Cria um pedido")
	public ResponseEntity<PedidoDTO> inserir(@RequestBody  PedidoDTO pedidoInsDTO){
		PedidoDTO pedidoDTO = pedidoService.inserir(pedidoInsDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoDTO);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Deleta um pedido especifico")
	public ResponseEntity<Void> deletar(@PathVariable Long id){

	PedidoDTO pedidoExists = pedidoService.buscar(id);
	if (pedidoExists != null) {
		pedidoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	return ResponseEntity.notFound().build();
}	
	
	@PostMapping("/recompra/{clienteId}")
	@Operation(
	        summary = "Recompra do último pedido de um cliente",
	        description = "Cria um novo pedido idêntico ao último pedido feito pelo cliente informado"
	    )
	
	public ResponseEntity<PedidoDTO> recompraUltimoPedido(
			@Parameter(description = "ID do cliente que deseja repetir o último pedido")
			@PathVariable Long clienteId){
		try {
			PedidoDTO novoPedido = recompraService.recompraUltimoPedido(clienteId);
			return ResponseEntity.ok(novoPedido);
		} catch (RuntimeException ex) {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	
	
	

}
