package org.urban.springbootcafeteria.service;

import org.urban.springbootcafeteria.dto.request.VentaRequest;
import org.urban.springbootcafeteria.dto.response.VentaResponse;
import java.util.List;

public interface VentaService {
    Integer crear(VentaRequest request);
    VentaResponse obtenerPorId(Integer id);
    VentaResponse actualizar(Integer id, VentaRequest request);
    void eliminar(Integer id);
    List<VentaResponse> listarTodos();
    List<VentaResponse> listarPorCliente(Integer idCliente);
}
