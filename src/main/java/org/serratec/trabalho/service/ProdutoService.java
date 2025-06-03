package org.serratec.trabalho.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.trabalho.domain.Categoria;
import org.serratec.trabalho.domain.Produto;
import org.serratec.trabalho.dto.ProdutoDTO;
import org.serratec.trabalho.repository.CategoriaRepository;
import org.serratec.trabalho.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
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
    
    //Produto por categoria
    public List<ProdutoDTO> findByCategoriaId(Long id) {
    	List<Produto> produtos = produtoRepository.findAll();
    	List<ProdutoDTO> produtosDTO = produtos.stream()
    			.filter(produto -> produto.getCategoria() != null && produto.getCategoria().getId().equals(id))
    			.map(ProdutoDTO :: new)
    			.toList();
    			
    	return produtosDTO;		
    }

    // Inserir novo produto
    public ProdutoDTO inserir(ProdutoDTO produtoDTO) {
        Produto produto = toEntity(produtoDTO);
        Categoria categoria = categoriaRepository.findById(produtoDTO.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        produto = produtoRepository.save(produto);
        return new ProdutoDTO(produto);
    }

    // Atualizar produto existente
    public ProdutoDTO atualizar(Long id, ProdutoDTO dto) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            produto.setNome(dto.getNome());
            produto.setPreco(dto.getPreco());
            produto.setEstoque(dto.getEstoque());
            
            Categoria cat = categoriaRepository.findById(dto.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada! Reveja o campo e tente novamente."));
            
            produto.setCategoria(cat);
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
    public Produto toEntity(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());
       
        Categoria cat = categoriaRepository.findById(dto.getCategoria().getId())
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada! Reveja o campo e tente novamente."));
            
        produto.setCategoria(cat);
        return produto;
    }

    // Exists by ID 
    public boolean existsById(Long id) {
        return produtoRepository.existsById(id);
    }
}
