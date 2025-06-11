package org.urban.springbootcafeteria.Repository;

import org.springframework.stereotype.Repository;
import org.urban.springbootcafeteria.Entitie.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}