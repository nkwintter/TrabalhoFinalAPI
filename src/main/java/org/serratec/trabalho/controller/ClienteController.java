package org.serratec.trabalho.controller;

import java.util.List;

import org.serratec.trabalho.domain.EnderecoViaCep;
import org.serratec.trabalho.dto.ClienteDTO;
import org.serratec.trabalho.dto.PedidoDTO;
import org.serratec.trabalho.security.JwtUtil;
import org.serratec.trabalho.service.ClienteService;
import org.serratec.trabalho.service.PedidoService;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private SendEmailService emailService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	//Para o ADM
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> listar(){
		return ResponseEntity.ok(clienteService.buscarTodos());
	}
	
	//Para o ADM
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTO> buscar(@PathVariable Long id) {
		ClienteDTO dto = clienteService.buscar(id);
		
		if(dto != null) {
			return ResponseEntity.ok(dto);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	//Para user logado(self profile)
	@GetMapping("/me")
	public ResponseEntity<ClienteDTO> getMeuPerfil(@RequestHeader("Authorization") String authHeader){
		String token = authHeader.replace("Bearer ", "") ;
		String email = jwtUtil.extractUsername(token);
		
		ClienteDTO cliente = clienteService.FindByEmail(email);
		return ResponseEntity.ok(cliente);
	}
	
	//Todos os pedidos daquele cliente
	@GetMapping("/me/pedidos")
	public ResponseEntity<List<PedidoDTO>> getMeusPedidos(@RequestHeader("Authorization") String authHeader){
		String token = authHeader.replace("Bearer ", "");
		String email = jwtUtil.extractUsername(token);
		
		List<PedidoDTO> pedidosDTO = pedidoService.BuscarPedidosUser(email);
		
		if(pedidosDTO.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(pedidosDTO);
		
	}
	
	//Qualquer pessoa(Cadastro)
	@PostMapping
	public ResponseEntity<ClienteDTO> inserir(@RequestBody ClienteDTO clienteInsDTO){
		
		EnderecoViaCep enderecoapi = ViacepApiService.consultarViaCep(clienteInsDTO.getEndereco().getCep());
		
		clienteInsDTO.getEndereco().setBairro(enderecoapi.getBairro());
		clienteInsDTO.getEndereco().setLocalidade(enderecoapi.getLocalidade());
		clienteInsDTO.getEndereco().setLogradouro(enderecoapi.getLogradouro());
		clienteInsDTO.getEndereco().setUf(enderecoapi.getUf());
		
		clienteInsDTO = clienteService.inserir(clienteInsDTO);
		
		//envio do email
		emailService.SendEmailCadastrado(clienteInsDTO.getEmail(), "Cadastro realizado com sucesso!", clienteInsDTO.getNome());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteInsDTO);
	}
	
	//Post de pedido
	@PostMapping("/me/pedidos")
	public ResponseEntity<PedidoDTO> inserir(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody PedidoDTO pedidoInsDTO){
		
		String token = authHeader.replace("Bearer ", "");
		String email = jwtUtil.extractUsername(token);
		
		PedidoDTO pedidoDTO = pedidoService.inserirPedidoUser(email, pedidoInsDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoDTO);
	}
	
	//Para user logado
	@PutMapping("/me")
	public ResponseEntity<ClienteDTO> atualizarMeuPerfil(
			@RequestHeader("Authorization") String authHeader, @RequestBody ClienteDTO dto){
		
		String token = authHeader.replace("Bearer ", "");
	    String email = jwtUtil.extractUsername(token);
	    
	    ClienteDTO clienteUpdated = clienteService.updateByEmail(email, dto);
	    return ResponseEntity.ok(clienteUpdated);
	}
	
	//Para user logado
	@DeleteMapping("/me")
	public ResponseEntity<Void> deletarMeuPerfil(@RequestHeader("Authorization") String authHeader){
		
		String token = authHeader.replace("Bearer ", "");
		String email = jwtUtil.extractUsername(token);
		
		clienteService.deleteByEmail(email);
		
		return ResponseEntity.noContent().build();
	}
	
}
