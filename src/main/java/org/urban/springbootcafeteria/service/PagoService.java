package org.urban.springbootcafeteria.service;

import org.urban.springbootcafeteria.dto.request.PagoRequest;
import org.urban.springbootcafeteria.dto.response.PagoResponse;
import org.urban.springbootcafeteria.entitie.Pago;
import java.util.List;

public interface PagoService {
    Integer crear(PagoRequest request);
    PagoResponse obtenerPorId(Integer id);
    PagoResponse actualizar(Integer id, PagoRequest request);
    void eliminar(Integer id);
    List<PagoResponse> listarTodos();
    List<PagoResponse> listarPorCliente(Integer idCliente);
    Pago obtenerPorPreferenceId(String preferenceId);
}
