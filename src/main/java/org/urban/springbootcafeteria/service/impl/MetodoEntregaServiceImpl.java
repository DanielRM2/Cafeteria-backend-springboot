package org.urban.springbootcafeteria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.dto.request.MetodoEntregaRequest;
import org.urban.springbootcafeteria.dto.response.MetodoEntregaResponse;
import org.urban.springbootcafeteria.entitie.MetodoEntrega;
import org.urban.springbootcafeteria.repository.MetodoEntregaRepository;
import org.urban.springbootcafeteria.service.MetodoEntregaService;

import java.util.List;

/**
 * Implementación del servicio de métodos de entrega.
 */
@Service
public class MetodoEntregaServiceImpl implements MetodoEntregaService {

    @Autowired
    private MetodoEntregaRepository metodoEntregaRepository;

    @Override
    public List<MetodoEntregaResponse> listarTodos() {
        return metodoEntregaRepository.findAll().stream()
                .map(m -> {
                    MetodoEntregaResponse response = new MetodoEntregaResponse();
                    response.setIdMetodoEntrega(m.getIdMetodoEntrega());
                    response.setNombre(m.getNombre());
                    response.setCosto(m.getCosto());
                    return response;
                })
                .toList();
    }

    @Override
    public MetodoEntregaResponse crear(MetodoEntregaRequest request) {
        MetodoEntrega metodo = new MetodoEntrega();
        metodo.setNombre(request.getNombre());
        metodo.setCosto(request.getCosto());
        metodoEntregaRepository.save(metodo);
        return mapToResponse(metodo);
    }

    @Override
    public MetodoEntregaResponse obtenerPorId(Integer id) {
        MetodoEntrega metodo = metodoEntregaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Método de entrega no encontrado"));
        return mapToResponse(metodo);
    }

    @Override
    public MetodoEntregaResponse actualizar(Integer id, MetodoEntregaRequest request) {
        MetodoEntrega metodo = metodoEntregaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Método de entrega no encontrado"));
        metodo.setNombre(request.getNombre());
        metodo.setCosto(request.getCosto());
        metodoEntregaRepository.save(metodo);
        return mapToResponse(metodo);
    }

    @Override
    public void eliminar(Integer id) {
        metodoEntregaRepository.deleteById(id);
    }

    private MetodoEntregaResponse mapToResponse(MetodoEntrega m) {
        MetodoEntregaResponse response = new MetodoEntregaResponse();
        response.setIdMetodoEntrega(m.getIdMetodoEntrega());
        response.setNombre(m.getNombre());
        response.setCosto(m.getCosto());
        return response;
    }
}
