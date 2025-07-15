package org.urban.springbootcafeteria.service;

import org.urban.springbootcafeteria.dto.request.RolRequest;
import org.urban.springbootcafeteria.dto.response.RolResponse;
import java.util.List;

public interface RolService {
    Integer crear(RolRequest request);
    RolResponse obtenerPorId(Integer id);
    RolResponse actualizar(Integer id, RolRequest request);
    void eliminar(Integer id);
    List<RolResponse> listarTodos();
}
