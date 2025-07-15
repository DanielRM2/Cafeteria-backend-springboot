package org.urban.springbootcafeteria.service;

import org.urban.springbootcafeteria.dto.request.DireccionClienteRequest;
import org.urban.springbootcafeteria.dto.response.DireccionClienteResponse;
import java.util.List;

public interface DireccionClienteService {
    Integer crear(DireccionClienteRequest request);
    DireccionClienteResponse obtenerPorId(Integer id);
    DireccionClienteResponse actualizar(Integer id, DireccionClienteRequest request);
    void eliminar(Integer id);
    List<DireccionClienteResponse> listarTodos();
    List<DireccionClienteResponse> listarPorCliente(Integer idCliente);
    DireccionClienteResponse marcarComoPredeterminada(Integer id);
}
