package org.urban.springbootcafeteria.service;

import org.urban.springbootcafeteria.dto.request.StaffRequest;
import org.urban.springbootcafeteria.dto.request.LoginRequest;
import org.urban.springbootcafeteria.dto.response.StaffResponse;
import org.urban.springbootcafeteria.dto.response.AuthStaffResponse;
import java.util.List;

public interface StaffService {
    // CRUD básico y listar
    String registrar(StaffRequest request);
    AuthStaffResponse login(LoginRequest request);
    // CRUD
    // Eliminado el método crear porque /registro lo cubre
    StaffResponse obtenerPorId(Integer id);
    StaffResponse actualizar(Integer id, StaffRequest request);
    void eliminar(Integer id);
    void cambiarEstado(Integer id, boolean activo);
    List<StaffResponse> listarTodos();
}
