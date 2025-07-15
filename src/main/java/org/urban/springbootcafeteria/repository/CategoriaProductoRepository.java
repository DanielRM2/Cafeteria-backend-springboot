package org.urban.springbootcafeteria.repository;

import org.urban.springbootcafeteria.entitie.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Integer> {
    Optional<CategoriaProducto> findByNombreCategoria(String nombreCategoria);
}
