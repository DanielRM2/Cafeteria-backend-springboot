package org.urban.springbootcafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urban.springbootcafeteria.entitie.DetallePedido;
import org.urban.springbootcafeteria.entitie.Pedido;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    List<DetallePedido> findByPedido(Pedido pedido);
}
