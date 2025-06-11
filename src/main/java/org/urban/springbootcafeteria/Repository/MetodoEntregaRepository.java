package org.urban.springbootcafeteria.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.urban.springbootcafeteria.Entitie.MetodoEntrega;

@Repository
public interface MetodoEntregaRepository extends JpaRepository<MetodoEntrega, Integer> {

}
