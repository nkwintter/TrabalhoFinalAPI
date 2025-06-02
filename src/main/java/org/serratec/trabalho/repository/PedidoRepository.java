package org.serratec.trabalho.repository;

import java.util.List;

import org.serratec.trabalho.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository <Pedido, Long>{
    @Query("SELECT p FROM Pedido p JOIN p.itens i WHERE p.cliente.id = :clienteId AND i.produto.id = :produtoId")
    List<Pedido> findPedidosByClienteEProduto(Long clienteId, Long produtoId);
}
