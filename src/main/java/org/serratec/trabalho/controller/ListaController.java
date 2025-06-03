package org.serratec.trabalho.controller;

import org.serratec.trabalho.domain.ListaDesejos;
import org.serratec.trabalho.domain.Produto;
import org.serratec.trabalho.dto.ListaDesejosDTO;
import org.serratec.trabalho.service.ListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/DivDasPlantas/listadesejos")
public class ListaController {

    @Autowired
    private ListaService listaDesejosService;

    @GetMapping("/{clienteId}")
    public ResponseEntity<ListaDesejosDTO> buscarLista(@PathVariable Long clienteId) {
        ListaDesejosDTO listaDTO = listaDesejosService.listarPorCliente(clienteId);
        return ResponseEntity.ok(listaDTO);
    }
//sem msg personalizada, lista a lista:
//    @PostMapping("/{clienteId}/produtos/{produtoId}")
//    public ResponseEntity<ListaDesejosDTO> adicionarProduto(@PathVariable Long clienteId, @PathVariable Long produtoId) {
//        listaDesejosService.adicionarProduto(clienteId, produtoId);
//        return ResponseEntity.ok(listaDesejosService.listarPorCliente(clienteId));
//    }
    @PostMapping("/{clienteId}/produtos/{produtoId}")
    public ResponseEntity<String> adicionarProduto(@PathVariable Long clienteId, @PathVariable Long produtoId) {
        ListaDesejos lista = listaDesejosService.adicionarProduto(clienteId, produtoId);
        Produto ultimo = lista.getProdutos().get(lista.getProdutos().size() - 1);
        String mensagem = "Produto: '" + ultimo.getNome() + "' adicionado com sucesso à sua lista de desejos!";
  return ResponseEntity.ok(mensagem);
    }
    
//    @DeleteMapping("/{clienteId}/produtos/{produtoId}")
//    public ResponseEntity<ListaDesejosDTO> removerProduto(@PathVariable Long clienteId, @PathVariable Long produtoId) {
//        listaDesejosService.removerProduto(clienteId, produtoId);
//        return ResponseEntity.ok(listaDesejosService.listarPorCliente(clienteId));
//    }

    @DeleteMapping("/{clienteId}/produtos/{produtoId}")
    public ResponseEntity<String> removerProduto(@PathVariable Long clienteId, @PathVariable Long produtoId) {
        ListaDesejos lista =listaDesejosService.removerProduto(clienteId, produtoId);
        Produto ultimo = lista.getProdutos().get(lista.getProdutos().size() - 1);
        String mensagem = "Produto: '" + ultimo.getNome() + "' adicionado com sucesso à sua lista de desejos!";
  return ResponseEntity.ok(mensagem);
    }

}
