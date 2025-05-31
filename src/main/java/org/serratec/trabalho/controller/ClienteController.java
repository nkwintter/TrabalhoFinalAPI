package org.serratec.trabalho.controller;

import java.util.List;

import org.serratec.trabalho.domain.EnderecoViaCep;
import org.serratec.trabalho.dto.ClienteDTO;
import org.serratec.trabalho.service.ClienteService;
import org.serratec.trabalho.service.SendEmailService;
import org.serratec.trabalho.service.ViacepApiService;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private SendEmailService emailService;
	
	
	@GetMapping
	@Operation(summary = "Listar todos os clientes")
	public ResponseEntity<List<ClienteDTO>> listar(){
		return ResponseEntity.ok(clienteService.buscarTodos());
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Listar um cliente especifico")
	public ResponseEntity<ClienteDTO> buscar(@PathVariable Long id) {
		ClienteDTO dto = clienteService.buscar(id);
		
		if(dto != null) {
			return ResponseEntity.ok(dto);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	@Operation(summary = "Criar um novo cliente")
	public ResponseEntity<ClienteDTO> inserir(@RequestBody  ClienteDTO clienteInsDTO){
		
		EnderecoViaCep enderecoapi = ViacepApiService.consultarViaCep(clienteInsDTO.getEndereco().getCep());
		
		clienteInsDTO.getEndereco().setBairro(enderecoapi.getBairro());
		clienteInsDTO.getEndereco().setLocalidade(enderecoapi.getLocalidade());
		clienteInsDTO.getEndereco().setLogradouro(enderecoapi.getLogradouro());
		clienteInsDTO.getEndereco().setUf(enderecoapi.getUf());
		
		clienteService.inserir(clienteInsDTO);
		
		emailService.SendEmailCadastrado(clienteInsDTO.getEmail(), "Cadastro realizado com sucesso!", clienteInsDTO.getNome());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteInsDTO);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Atualizar cliente")
	public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteDTO clienteDTO) {
	    ClienteDTO atualizado = clienteService.atualizar(id, clienteDTO);
	    if (atualizado != null) {
	        return ResponseEntity.ok(atualizado);
	    }
	    return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Remover cliente")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
	    ClienteDTO cliente = clienteService.buscar(id);
	    if (cliente != null) {
	        clienteService.deleteById(id);
	        
	        emailService.SendEmailRemovido(cliente.getEmail(), "Cadastro excluído com sucesso!", cliente.getNome());
	        return ResponseEntity.noContent().build();
	    }
	    return ResponseEntity.notFound().build();
	}
	
}
