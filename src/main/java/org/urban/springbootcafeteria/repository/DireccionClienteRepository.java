package org.urban.springbootcafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urban.springbootcafeteria.entitie.DireccionCliente;

import java.util.List;

public interface DireccionClienteRepository extends JpaRepository<DireccionCliente, Integer> {

    List<DireccionCliente> findByClienteIdAndPredeterminadaTrue(Integer idCliente);
}
