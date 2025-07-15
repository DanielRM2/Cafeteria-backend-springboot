package org.urban.springbootcafeteria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.dto.request.RolRequest;
import org.urban.springbootcafeteria.dto.response.RolResponse;
import org.urban.springbootcafeteria.entitie.Rol;
import org.urban.springbootcafeteria.repository.RolRepository;
import org.urban.springbootcafeteria.service.RolService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {
    @Autowired
    private RolRepository rolRepository;

    @Override
    public Integer crear(RolRequest request) {
        Rol rol = new Rol();
        rol.setNombre(request.getNombre());
        rolRepository.save(rol);
        return rol.getId();
    }

    @Override
    public RolResponse obtenerPorId(Integer id) {
        Rol rol = rolRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return mapToResponse(rol);
    }

    @Override
    public RolResponse actualizar(Integer id, RolRequest request) {
        Rol rol = rolRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rol.setNombre(request.getNombre());
        rolRepository.save(rol);
        return mapToResponse(rol);
    }

    @Override
    public void eliminar(Integer id) {
        rolRepository.deleteById(id);
    }

    @Override
    public List<RolResponse> listarTodos() {
        return rolRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private RolResponse mapToResponse(Rol rol) {
        RolResponse response = new RolResponse();
        response.setId(rol.getId());
        response.setNombre(rol.getNombre());
        return response;
    }
}
