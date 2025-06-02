package org.serratec.trabalho.controller;

import java.util.List;

import org.serratec.trabalho.dto.CategoriaDTO;
import org.serratec.trabalho.service.CategoriaService;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> listar(){
		return ResponseEntity.ok(categoriaService.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTO> buscar(@PathVariable Long id) {
		CategoriaDTO dto = categoriaService.buscar(id);
		
		if(dto != null) {
			return ResponseEntity.ok(dto);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	public ResponseEntity<CategoriaDTO> inserir(@RequestBody @Valid CategoriaDTO categoriaInsDTO){
		CategoriaDTO categoriaDTO = categoriaService.inserir(categoriaInsDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid CategoriaDTO categoriaDTO) {
	    CategoriaDTO atualizado = categoriaService.atualizar(id, categoriaDTO);
	    if (atualizado != null) {
	        return ResponseEntity.ok(atualizado);
	    }
	    return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
	    CategoriaDTO categoria = categoriaService.buscar(id);
	    if (categoria != null) {
	        categoriaService.deleteById(id);
	        return ResponseEntity.noContent().build();
	    }
	    return ResponseEntity.notFound().build();
	}
	
}
