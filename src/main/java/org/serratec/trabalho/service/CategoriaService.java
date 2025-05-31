package org.serratec.trabalho.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.trabalho.domain.Categoria;
import org.serratec.trabalho.dto.CategoriaDTO;
import org.serratec.trabalho.exception.CategoriaNaoEncontradaException;
import org.serratec.trabalho.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Listar todas
    public List<CategoriaDTO> listar() {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaDTO> categoriasDTO = new ArrayList<>();
        for (Categoria categoria : categorias) {
            categoriasDTO.add(new CategoriaDTO(categoria));
        }
        return categoriasDTO;
    }

    // Buscar por ID
    public CategoriaDTO buscar(Long id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isPresent()) {
            return new CategoriaDTO(categoriaOpt.get());
        }
        throw new CategoriaNaoEncontradaException("Categoria com ID " + id + " não foi encontrada.");
    }

    // Inserir nova
    public CategoriaDTO inserir(CategoriaDTO categoriaDTO) {
        Categoria categoria = toEntity(categoriaDTO);
        categoria = categoriaRepository.save(categoria);
        return new CategoriaDTO(categoria);
    }

    // Atualizar existente
    public CategoriaDTO atualizar(Long id, CategoriaDTO categoriaDTO) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();
            categoria.setNome(categoriaDTO.getNome());
            categoria = categoriaRepository.save(categoria);
            return new CategoriaDTO(categoria);
        }
        throw new CategoriaNaoEncontradaException("Categoria com ID " + id + " não foi encontrada.");
    }

    // Deletar por ID
    public void deleteById(Long id) {
Categoria categoriadel = categoriaRepository.findById(id) .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria com ID " + id + " não foi encontrada."));
       categoriaRepository.delete(categoriadel); }

    // Find by ID (opcional para o controller)
    public Optional<CategoriaDTO> findById(Long id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        return categoriaOpt.map(CategoriaDTO::new);
    }

    // Conversão DTO -> Entidade
    private Categoria toEntity(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setNome(dto.getNome());
        return categoria;
    }
}