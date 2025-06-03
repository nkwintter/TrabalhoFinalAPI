package org.serratec.trabalho.controller;

import java.util.List;

import org.serratec.trabalho.domain.EnderecoViaCep;
import org.serratec.trabalho.dto.ClienteDTO;
import org.serratec.trabalho.dto.PedidoDTO;
import org.serratec.trabalho.dto.PedidoDTOSimplificado;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	//Todos os pedidos daquele cliente
	@GetMapping("/me/pedidos")
	public ResponseEntity<List<PedidoDTOSimplificado>> getMeusPedidos(@RequestHeader("Authorization") String authHeader){
		String token = authHeader.replace("Bearer ", "");
		String email = jwtUtil.extractUsername(token);
		
		List<PedidoDTOSimplificado> pedidosDTO = pedidoService.BuscarPedidosUser(email);
		
		if(pedidosDTO.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(pedidosDTO);
	}}
//	}
//	
//	//Post de pedido
//	@PostMapping("/me/pedidos")
//	public ResponseEntity<PedidoDTOSimplificado> inserir(
//			@RequestHeader("Authorization") String authHeader,
//			@RequestBody PedidoDTO pedidoInsDTO){
//		
//		String token = authHeader.replace("Bearer ", "");
//		String email = jwtUtil.extractUsername(token);
//		
//		PedidoDTOSimplificado pedidoDTO = pedidoService.inserirPedidoUser(email, pedidoInsDTO);
//		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoDTO);
//	}
//
//    @GetMapping
//    @Operation(summary = "Lista todos os clientes", description = "Retorna todos os clientes cadastrados")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Retorna todos os clientes!", content = {
//            @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))
//        }),
//        @ApiResponse(responseCode = "401", description = "Não autorizado"),
//        @ApiResponse(responseCode = "403", description = "Não há permissão para acesso"),
//        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
//        @ApiResponse(responseCode = "505", description = "Exceção interna da aplicação")
//    })
//    public ResponseEntity<List<ClienteDTO>> listar() {
//        return ResponseEntity.ok(clienteService.buscarTodos());
//    }
//
//    @GetMapping("/{id}")
//    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente pelo ID")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = {
//            @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))
//        }),
//        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
//    })
//    public ResponseEntity<ClienteDTO> buscar(@PathVariable Long id) {
//        ClienteDTO dto = clienteService.buscar(id);
//
//        if (dto != null) {
//            return ResponseEntity.ok(dto);
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//
//    @GetMapping("/me")
//    @Operation(summary = "Obter perfil do cliente logado", description = "Retorna os dados do cliente autenticado pelo token JWT")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Perfil retornado com sucesso", content = {
//            @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))
//        }),
//        @ApiResponse(responseCode = "401", description = "Token inválido ou ausente")
//    })
//    public ResponseEntity<ClienteDTO> getMeuPerfil(@RequestHeader("Authorization") String authHeader) {
//        String token = authHeader.replace("Bearer ", "");
//        String email = jwtUtil.extractUsername(token);
//
//        ClienteDTO cliente = clienteService.FindByEmail(email);
//        return ResponseEntity.ok(cliente);
//    }
//
//    @PostMapping
//    @Operation(summary = "Cadastrar novo cliente", description = "Cria um novo cliente com base nos dados fornecidos")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
//        @ApiResponse(responseCode = "400", description = "Erro nos dados fornecidos")
//    })
//    public ResponseEntity<ClienteDTO> inserir(@RequestBody ClienteDTO clienteInsDTO) {
//
//        EnderecoViaCep enderecoapi = ViacepApiService.consultarViaCep(clienteInsDTO.getEndereco().getCep());
//
//        clienteInsDTO.getEndereco().setBairro(enderecoapi.getBairro());
//        clienteInsDTO.getEndereco().setLocalidade(enderecoapi.getLocalidade());
//        clienteInsDTO.getEndereco().setLogradouro(enderecoapi.getLogradouro());
//        clienteInsDTO.getEndereco().setUf(enderecoapi.getUf());
//
//        clienteInsDTO = clienteService.inserir(clienteInsDTO);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(clienteInsDTO);
//    }
//
//    @PutMapping("/me")
//    @Operation(summary = "Atualizar perfil do cliente logado", description = "Atualiza os dados do cliente autenticado")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
//        @ApiResponse(responseCode = "401", description = "Token inválido ou ausente")
//    })
//    public ResponseEntity<ClienteDTO> atualizarMeuPerfil(
//            @RequestHeader("Authorization") String authHeader, @RequestBody ClienteDTO dto) {
//
//        String token = authHeader.replace("Bearer ", "");
//        String email = jwtUtil.extractUsername(token);
//
//        ClienteDTO clienteUpdated = clienteService.updateByEmail(email, dto);
//        return ResponseEntity.ok(clienteUpdated);
//    }
//
//    @DeleteMapping("/me")
//    @Operation(summary = "Excluir perfil do cliente logado", description = "Remove o cliente autenticado do sistema")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
//        @ApiResponse(responseCode = "401", description = "Token inválido ou ausente")
//    })
//    public ResponseEntity<Void> deletarMeuPerfil(@RequestHeader("Authorization") String authHeader) {
//
//        String token = authHeader.replace("Bearer ", "");
//        String email = jwtUtil.extractUsername(token);
//
//        clienteService.deleteByEmail(email);
//
//        return ResponseEntity.noContent().build();
//    }
//
//}
//