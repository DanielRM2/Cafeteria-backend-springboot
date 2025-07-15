package org.urban.springbootcafeteria.service;

import org.urban.springbootcafeteria.dto.request.DetallePedidoRequest;
import org.urban.springbootcafeteria.dto.response.DetallePedidoResponse;
import java.util.List;

public interface DetallePedidoService {
    Integer crear(DetallePedidoRequest request);
    DetallePedidoResponse obtenerPorId(Integer id);
    DetallePedidoResponse actualizar(Integer id, DetallePedidoRequest request);
    void eliminar(Integer id);
    List<DetallePedidoResponse> listarTodos();
}
