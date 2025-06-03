package org.serratec.trabalho.controller;

import java.util.List;

import org.serratec.trabalho.domain.Avaliacao;
import org.serratec.trabalho.domain.Cliente;
import org.serratec.trabalho.dto.AvaliacaoDTO;
import org.serratec.trabalho.repository.ClienteRepository;
import org.serratec.trabalho.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/avaliacoes")
@Tag(name = "Avaliações", description = "Endpoints para gerenciamento de avaliações de produtos")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    @Operation(summary = "Criar avaliação", description = "Permite que um cliente autenticado crie uma avaliação para um produto que ele comprou.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação criada com sucesso",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Avaliacao.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<?> criarAvaliacao(@RequestBody @Valid AvaliacaoDTO dto, Authentication auth) {
        Long clienteId = pegarClienteIdDoAuth(auth);
        Avaliacao aval = avaliacaoService.criarAvaliacao(clienteId, dto);
        return ResponseEntity.ok(aval);
    }

    @GetMapping("/produto/{produtoId}")
    @Operation(summary = "Listar avaliações por produto", description = "Retorna todas as avaliações feitas para um determinado produto.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliações listadas com sucesso",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = Avaliacao.class))),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<List<Avaliacao>> listarPorProduto(@PathVariable Long produtoId) {
        List<Avaliacao> avaliacoes = avaliacaoService.listarAvaliacoesPorProduto(produtoId);
        return ResponseEntity.ok(avaliacoes);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar avaliação", description = "Permite que um cliente edite uma avaliação que ele mesmo fez.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação editada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Cliente não tem permissão para editar esta avaliação"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public ResponseEntity<?> editarAvaliacao(@PathVariable Long id, @RequestBody @Valid AvaliacaoDTO dto, Authentication auth) {
        Long clienteId = pegarClienteIdDoAuth(auth);
        Avaliacao aval = avaliacaoService.editarAvaliacao(clienteId, id, dto);
        return ResponseEntity.ok(aval);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar avaliação", description = "Permite que um cliente exclua uma avaliação que ele mesmo fez.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação deletada com sucesso"),
        @ApiResponse(responseCode = "403", description = "Cliente não tem permissão para deletar esta avaliação"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public ResponseEntity<?> deletarAvaliacao(@PathVariable Long id, Authentication auth) {
        Long clienteId = pegarClienteIdDoAuth(auth);
        avaliacaoService.deletarAvaliacao(clienteId, id);
        return ResponseEntity.ok("Avaliação deletada com sucesso");
    }

    private Long pegarClienteIdDoAuth(Authentication auth) {
        String email = auth.getName();
        Cliente cliente = clienteRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return cliente.getId();
    }
}
