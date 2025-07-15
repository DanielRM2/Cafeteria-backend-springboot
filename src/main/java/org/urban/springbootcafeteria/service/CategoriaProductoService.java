package org.urban.springbootcafeteria.service;

import org.urban.springbootcafeteria.dto.request.CategoriaProductoRequest;
import org.urban.springbootcafeteria.dto.response.CategoriaProductoResponse;
import java.util.List;

public interface CategoriaProductoService {
    Integer crear(CategoriaProductoRequest request);
    CategoriaProductoResponse obtenerPorId(Integer id);
    CategoriaProductoResponse actualizar(Integer id, CategoriaProductoRequest request);
    void eliminar(Integer id);
    List<CategoriaProductoResponse> listarTodos();
}
