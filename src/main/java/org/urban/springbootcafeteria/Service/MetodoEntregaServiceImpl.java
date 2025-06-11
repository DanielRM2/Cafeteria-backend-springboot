package org.urban.springbootcafeteria.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.Dto.MetodoEntregaResponse;
import org.urban.springbootcafeteria.Repository.MetodoEntregaRepository;

import java.util.List;


@Service
public class MetodoEntregaServiceImpl implements MetodoEntregaService{

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
}
