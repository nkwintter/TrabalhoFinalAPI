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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private SendEmailService emailService;
	
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> listar(){
		return ResponseEntity.ok(clienteService.buscarTodos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTO> buscar(@PathVariable Long id) {
		ClienteDTO dto = clienteService.buscar(id);
		
		if(dto != null) {
			return ResponseEntity.ok(dto);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	public ResponseEntity<ClienteDTO> inserir(@RequestBody  ClienteDTO clienteInsDTO){
		
		EnderecoViaCep enderecoapi = ViacepApiService.consultarViaCep(clienteInsDTO.getEndereco().getCep());
		
		clienteInsDTO.getEndereco().setBairro(enderecoapi.getBairro());
		clienteInsDTO.getEndereco().setLocalidade(enderecoapi.getLocalidade());
		clienteInsDTO.getEndereco().setLogradouro(enderecoapi.getLogradouro());
		clienteInsDTO.getEndereco().setUf(enderecoapi.getUf());
		
		clienteService.inserir(clienteInsDTO);
		
		String msg = String.format("Olá, %s! Seu cadastro foi realizado com sucesso!", clienteInsDTO.getNome());
		emailService.SendEmail(clienteInsDTO.getEmail(), "Cadastro realizado com sucesso!", msg);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteInsDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteDTO clienteDTO) {
	    ClienteDTO atualizado = clienteService.atualizar(id, clienteDTO);
	    if (atualizado != null) {
	        return ResponseEntity.ok(atualizado);
	    }
	    return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
	    ClienteDTO cliente = clienteService.buscar(id);
	    if (cliente != null) {
	        clienteService.deleteById(id);
	        String msg = String.format("Olá, %s! Seu cadastro foi excluído com sucesso!", cliente.getNome());
	        emailService.SendEmail(cliente.getEmail(), "Cadastro excluído com sucesso!", msg);
	        return ResponseEntity.noContent().build();
	    }
	    return ResponseEntity.notFound().build();
	}
	
}
