package org.serratec.trabalho.controller;

import java.util.List;

import org.serratec.trabalho.domain.Produto;
import org.serratec.trabalho.dto.ProdutoDTO;
import org.serratec.trabalho.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	
    @Operation(
        summary = "Lista todas as Plantas",
        description = "Retorna todas as Plantas cadastradas no estoque"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Retorna todas as Plantas!",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Produto.class)
                )
            }
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado"
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Não há permissão para acesso"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Recurso não encontrado"
        ),
        @ApiResponse(
                responseCode = "505",
                description = "Exceção interna da aplicação"
        ),
    })
	public ResponseEntity<List<ProdutoDTO>> listar(){
		return ResponseEntity.ok(produtoService.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long id) {
		ProdutoDTO dto = produtoService.buscar(id);
		if(dto != null) {
			return ResponseEntity.ok(dto);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	public  ResponseEntity<ProdutoDTO> inserir(@RequestBody @Valid ProdutoDTO produtoInsDTO) {
		ProdutoDTO produtoDTO = produtoService.inserir(produtoInsDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoDTO produtoDTO) {
	    ProdutoDTO atualizado = produtoService.atualizar(id, produtoDTO);
	    if (atualizado != null) {
	        return ResponseEntity.ok(atualizado);
	    }
	    return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
	    if (produtoService.existsById(id)) {
	        produtoService.deleteById(id);
	        return ResponseEntity.noContent().build();
	    }
	    return ResponseEntity.notFound().build();
	}
	
}
