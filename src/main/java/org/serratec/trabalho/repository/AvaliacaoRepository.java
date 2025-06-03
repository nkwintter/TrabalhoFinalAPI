package org.serratec.trabalho.repository;

import java.util.List;
import java.util.Optional;

import org.serratec.trabalho.domain.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    
    List<Avaliacao> findByProdutoId(Long produtoId);

    Optional<Avaliacao> findByClienteIdAndProdutoId(Long clienteId, Long produtoId);
}