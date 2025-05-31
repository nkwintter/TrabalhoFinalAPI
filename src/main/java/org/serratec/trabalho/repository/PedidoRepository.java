package org.serratec.trabalho.repository;

import java.util.Optional;

import org.serratec.trabalho.domain.Cliente;
import org.serratec.trabalho.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository <Pedido, Long>{
	
	Optional<Pedido> findTopByClienteOrderByDataCriacaoDesc(Cliente cliente);

}
