package org.urban.springbootcafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.urban.springbootcafeteria.entitie.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    @Query("SELECT i FROM Inventario i JOIN FETCH i.producto")
    List<Inventario> findAllWithProductos();
}