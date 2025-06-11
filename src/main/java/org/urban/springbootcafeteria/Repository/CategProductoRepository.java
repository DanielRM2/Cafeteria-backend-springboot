package org.urban.springbootcafeteria.Repository;

import org.urban.springbootcafeteria.Entitie.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategProductoRepository extends JpaRepository<CategoriaProducto, Integer> {

}