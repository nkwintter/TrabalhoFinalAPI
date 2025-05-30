package org.serratec.trabalho.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.trabalho.domain.Categoria;
import org.serratec.trabalho.domain.Produto;
import org.serratec.trabalho.dto.ProdutoDTO;
import org.serratec.trabalho.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Listar todos
    public List<ProdutoDTO> listar() {
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoDTO> produtosDTO = new ArrayList<>();
        for (Produto produto : produtos) {
            produtosDTO.add(new ProdutoDTO(produto));
        }
        return produtosDTO;
    }

    // Buscar por ID
    public ProdutoDTO buscar(Long id) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        return produtoOpt.map(ProdutoDTO::new).orElse(null);
    }
    
    public Produto buscarId(Long id) {
    	Optional<Produto> produtoOpt = produtoRepository.findById(id);
        return produtoOpt.orElse(null);
    }

    // Inserir novo produto
    public ProdutoDTO inserir(ProdutoDTO produtoDTO) {
        Produto produto = toEntity(produtoDTO);
        produto = produtoRepository.save(produto);
        return new ProdutoDTO(produto);
    }

    // Atualizar produto existente
    public ProdutoDTO atualizar(Long id, ProdutoDTO produtoDTO) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            produto.setNome(produtoDTO.getNome());
            produto.setPreco(produtoDTO.getPreco());
            produto.setCategoria(new Categoria(produtoDTO.getCategoria().getId(), produtoDTO.getCategoria().getNome())); 
            produto = produtoRepository.save(produto);
            return new ProdutoDTO(produto);
        }
        return null;
    }

    // Deletar por ID
    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }

    // Conversão DTO -> Entidade
    private Produto toEntity(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setId(dto.getId());
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(new Categoria(dto.getCategoria().getId(), dto.getCategoria().getNome()));
        return produto;
    }

    // Exists by ID 
    public boolean existsById(Long id) {
        return produtoRepository.existsById(id);
    }
}
