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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	//Para ADM
	@GetMapping
	@Operation(summary = "Lista todos os pedidos", description = "Retorna todos os pedidos realizados")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Retorna todos os pedidos!", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PedidoDTO.class))
		}),
		@ApiResponse(responseCode = "401", description = "Não autorizado"),
		@ApiResponse(responseCode = "403", description = "Não há permissão para acesso"),
		@ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
		@ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
	})
	public ResponseEntity<List<PedidoDTO>> listar(){
		return ResponseEntity.ok(pedidoService.buscarTodos());
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Buscar pedido por ID", description = "Retorna os dados de um pedido específico pelo ID.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Pedido encontrado"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado")
	})
	public ResponseEntity<PedidoDTO> buscar(@PathVariable Long id) {
		PedidoDTO dto = pedidoService.buscar(id);
		return ResponseEntity.ok(dto);
	}
	

	// @PostMapping
	// @Operation(summary = "Cadastrar novo pedido", description = "Insere um novo pedido no banco de dados.")
	// @ApiResponses(value = {
	// 	@ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
	// 	@ApiResponse(responseCode = "400", description = "Dados inválidos")
	// })
	// public ResponseEntity<PedidoDTO> inserir(@RequestBody PedidoDTO pedidoInsDTO){
	// 	PedidoDTO pedidoDTO = pedidoService.inserir(pedidoInsDTO);
	// 	return ResponseEntity.status(HttpStatus.CREATED).body(pedidoDTO);
	// }

	
	@DeleteMapping("/{id}")
	@Operation(summary = "Excluir pedido", description = "Remove um pedido do banco de dados pelo ID.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Pedido não encontrado")
	})
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		PedidoDTO pedidoExists = pedidoService.buscar(id);
		if (pedidoExists != null) {
			pedidoService.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
