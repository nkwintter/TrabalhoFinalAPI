package org.serratec.trabalho.controller;

import java.util.List;

import org.serratec.trabalho.dto.PedidoDTO;
import org.serratec.trabalho.service.PedidoService;
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

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping
	public ResponseEntity<List<PedidoDTO>> listar(){
		return ResponseEntity.ok(pedidoService.buscarTodos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PedidoDTO> buscar(@PathVariable Long id) {
		PedidoDTO dto = pedidoService.buscar(id);
		if(dto != null) {
			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<PedidoDTO> inserir(@RequestBody  PedidoDTO pedidoInsDTO){
		PedidoDTO pedidoDTO = pedidoService.inserir(pedidoInsDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoDTO);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id){

	PedidoDTO pedidoExists = pedidoService.buscar(id);
	if (pedidoExists != null) {
		pedidoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	return ResponseEntity.notFound().build();
}	

}
