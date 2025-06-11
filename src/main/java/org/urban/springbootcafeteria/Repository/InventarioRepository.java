package org.urban.springbootcafeteria.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.urban.springbootcafeteria.Entitie.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

}