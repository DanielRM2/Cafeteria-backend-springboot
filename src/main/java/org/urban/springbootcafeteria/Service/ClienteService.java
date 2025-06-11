package org.urban.springbootcafeteria.Service;

import org.urban.springbootcafeteria.Dto.ClienteRequest;
import org.urban.springbootcafeteria.Dto.ClienteResponse;

public interface ClienteService {
    ClienteResponse registrarCliente(ClienteRequest clienteRequest);
    boolean validarCredenciales(String email, String contrasena);
    ClienteResponse obtenerClientePorEmail(String email);
    ClienteResponse obtenerClientePorId(Integer id);
    void actualizarCliente(Integer id, ClienteRequest clienteRequest);
    void eliminarCliente(Integer id);
}