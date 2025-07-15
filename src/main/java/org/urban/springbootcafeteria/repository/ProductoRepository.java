package org.urban.springbootcafeteria.repository;

import org.urban.springbootcafeteria.entitie.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    // Trae los primeros 4 productos ordenados por ID ascendente
    List<Producto> findTop4ByOrderByIdProductoAsc();

}