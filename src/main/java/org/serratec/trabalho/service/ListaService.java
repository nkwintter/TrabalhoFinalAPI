package org.serratec.trabalho.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.serratec.trabalho.domain.Cliente;
import org.serratec.trabalho.domain.ListaDesejos;
import org.serratec.trabalho.domain.Produto;
import org.serratec.trabalho.dto.ListaDesejosDTO;
import org.serratec.trabalho.dto.ProdutoDTO;
import org.serratec.trabalho.exception.ListaDesejosException;
import org.serratec.trabalho.repository.ClienteRepository;
import org.serratec.trabalho.repository.ListaDesejoRepository;
import org.serratec.trabalho.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListaService {

    @Autowired
    private ListaDesejoRepository listaDesejoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    public ListaDesejos buscarOuCriarLista(Long clienteId) {
        Optional<ListaDesejos> listaOpt = listaDesejoRepository.findByClienteId(clienteId);
        if (listaOpt.isPresent()) { return listaOpt.get();
        } else {
            Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new ListaDesejosException("Cliente com ID: " + clienteId +" não encontrado! Por favor, tente novamente."));
            ListaDesejos novaLista = new ListaDesejos();
            novaLista.setCliente(cliente);
            return listaDesejoRepository.save(novaLista);
        }
    }
    

    public ListaDesejos adicionarProduto(Long clienteId, Long produtoId) {
        ListaDesejos lista = buscarOuCriarLista(clienteId);
        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new ListaDesejosException("Produto não encontrado"));
        
        if (lista.getProdutos().contains(produto)) {
            throw new ListaDesejosException("Este produto já está na lista de desejos");
        }
        
        lista.getProdutos().add(produto);
        lista.setDataCriacao(LocalDateTime.now());
        return listaDesejoRepository.save(lista);
    }
    

    public ListaDesejos removerProduto(Long clienteId, Long produtoId) {
        ListaDesejos lista = buscarOuCriarLista(clienteId);
        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new ListaDesejosException("Produto com o ID: " + produtoId+ "não encontrado"));

        if (lista.getProdutos().contains(produto)) {lista.getProdutos().remove(produto);
            return listaDesejoRepository.save(lista);
        } else {
            throw new ListaDesejosException(produto.getNome() +" não está na lista de desejos!");
        }
    }

    
    // Retorna a lista de desejos como DTO p mandar p front
    public ListaDesejosDTO listarPorCliente(Long clienteId) {
        ListaDesejos lista = buscarOuCriarLista(clienteId);

        ListaDesejosDTO dto = new ListaDesejosDTO();
        dto.setId(lista.getId());
        dto.setClienteId(lista.getCliente().getId());
        dto.setData(lista.getDataCriacao());
        
        List<ProdutoDTO> produtosDTO = lista.getProdutos().stream()
                .map(ProdutoDTO::new)
                .toList(); 
        dto.setListadedesejos(produtosDTO);
        return dto;
    }
    
    public void limparLista(Long clienteId) {
        ListaDesejos lista = buscarOuCriarLista(clienteId);
        lista.getProdutos().clear();
        listaDesejoRepository.save(lista);
    }
    
 // ---
    public void verificarUsuario(Long clienteId, String emailClienteLogado) {
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new ListaDesejosException("Cliente não encontrado"));
        
        if (!cliente.getEmail().equals(emailClienteLogado)) {
            throw new ListaDesejosException("Acesso não autorizado a esta lista de desejos");
        }
    }

    
}
