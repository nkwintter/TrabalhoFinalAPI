package org.serratec.trabalho.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.serratec.trabalho.domain.Avaliacao;
import org.serratec.trabalho.domain.Cliente;
import org.serratec.trabalho.dto.AvaliacaoDTO;
import org.serratec.trabalho.repository.ClienteRepository;
import org.serratec.trabalho.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<?> criarAvaliacao(@RequestBody @Valid AvaliacaoDTO dto, Authentication auth) {
        Long clienteId = pegarClienteIdDoAuth(auth);
        Avaliacao aval = avaliacaoService.criarAvaliacao(clienteId, dto);
        return ResponseEntity.ok(aval);
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<Avaliacao>> listarPorProduto(@PathVariable Long produtoId) {
        List<Avaliacao> avaliacoes = avaliacaoService.listarAvaliacoesPorProduto(produtoId);
        return ResponseEntity.ok(avaliacoes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarAvaliacao(@PathVariable Long id, @RequestBody @Valid AvaliacaoDTO dto, Authentication auth) {
        Long clienteId = pegarClienteIdDoAuth(auth);
        Avaliacao aval = avaliacaoService.editarAvaliacao(clienteId, id, dto);
        return ResponseEntity.ok(aval);
    }

    @DeleteMapping("/{id}")
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
