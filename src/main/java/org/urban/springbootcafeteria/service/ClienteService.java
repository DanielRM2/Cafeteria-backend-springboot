package org.urban.springbootcafeteria.service;

import org.urban.springbootcafeteria.dto.request.ClienteRequest;
import org.urban.springbootcafeteria.dto.request.LoginRequest;
import org.urban.springbootcafeteria.dto.response.AuthClienteResponse;
import org.urban.springbootcafeteria.dto.response.ClienteResponse;
import java.util.List;

public interface ClienteService {

    String registrar(ClienteRequest request);
    AuthClienteResponse login(LoginRequest request);
    // Eliminado el método crear porque /registro lo cubre
    ClienteResponse obtenerPorId(Integer id);
    ClienteResponse actualizar(Integer id, ClienteRequest request);
    void eliminar(Integer id);
    void cambiarEstado(Integer id, boolean activo);
    List<ClienteResponse> listarTodos();
}
