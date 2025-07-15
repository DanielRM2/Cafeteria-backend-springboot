package org.urban.springbootcafeteria.service;

import org.urban.springbootcafeteria.dto.request.InventarioRequest;
import org.urban.springbootcafeteria.dto.response.InventarioResponse;
import java.util.List;

public interface InventarioService {
    InventarioResponse agregarInventario(InventarioRequest inventarioRequest);
    List<InventarioResponse> obtenerTodoElInventario();
    InventarioResponse obtenerInventarioPorId(Integer id);
    InventarioResponse actualizarInventario(Integer id, InventarioRequest inventarioRequest);
    void eliminarInventario(Integer id);
}
