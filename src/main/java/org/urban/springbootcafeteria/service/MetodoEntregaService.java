package org.urban.springbootcafeteria.service;

import org.urban.springbootcafeteria.dto.response.MetodoEntregaResponse;
import org.urban.springbootcafeteria.dto.request.MetodoEntregaRequest;

import java.util.List;

public interface MetodoEntregaService {

    // CRUD y listar
    List<MetodoEntregaResponse> listarTodos();
    MetodoEntregaResponse crear(MetodoEntregaRequest request);
    MetodoEntregaResponse obtenerPorId(Integer id);
    MetodoEntregaResponse actualizar(Integer id, MetodoEntregaRequest request);
    void eliminar(Integer id);

}
