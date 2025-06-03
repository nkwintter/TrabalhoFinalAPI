package org.serratec.trabalho.repository;

import java.util.Optional;

import org.serratec.trabalho.domain.ListaDesejos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaDesejoRepository extends JpaRepository<ListaDesejos, Long>{

	Optional<ListaDesejos> findByClienteId(Long clienteId);

}
