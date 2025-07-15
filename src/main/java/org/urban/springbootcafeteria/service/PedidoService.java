package org.urban.springbootcafeteria.service;

import org.urban.springbootcafeteria.dto.request.PedidoRequest;
import org.urban.springbootcafeteria.dto.response.PedidoResponse;
import java.util.List;

public interface PedidoService {
    Integer crear(PedidoRequest request);
    PedidoResponse obtenerPorId(Integer id);
    PedidoResponse actualizar(Integer id, PedidoRequest request);
    void eliminar(Integer id);
    List<PedidoResponse> listarTodos();
    List<PedidoResponse> listarPorCliente(Integer idCliente);
}
