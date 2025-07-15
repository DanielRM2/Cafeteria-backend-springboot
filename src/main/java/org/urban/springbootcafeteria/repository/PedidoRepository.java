package org.urban.springbootcafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urban.springbootcafeteria.entitie.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
