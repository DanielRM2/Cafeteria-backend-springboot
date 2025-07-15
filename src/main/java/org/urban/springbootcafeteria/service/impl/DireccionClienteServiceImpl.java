package org.urban.springbootcafeteria.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.dto.request.DireccionClienteRequest;
import org.urban.springbootcafeteria.dto.response.DireccionClienteResponse;
import org.urban.springbootcafeteria.entitie.DireccionCliente;
import org.urban.springbootcafeteria.entitie.Cliente;
import org.urban.springbootcafeteria.repository.DireccionClienteRepository;
import org.urban.springbootcafeteria.repository.ClienteRepository;
import org.urban.springbootcafeteria.service.DireccionClienteService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DireccionClienteServiceImpl implements DireccionClienteService {
    @Autowired
    private DireccionClienteRepository direccionClienteRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Integer crear(DireccionClienteRequest request) {
        DireccionCliente direccion = new DireccionCliente();
        direccion.setDireccion(request.getDireccion());
        direccion.setReferencia(request.getReferencia());
        direccion.setDistrito(request.getDistrito());
        Cliente cliente = clienteRepository.findById(request.getIdCliente())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        direccion.setCliente(cliente);
        if (Boolean.TRUE.equals(request.getPredeterminada())) {
            desmarcarOtrasPredeterminadas(request.getIdCliente());
            direccion.setPredeterminada(true);
        } else {
            direccion.setPredeterminada(false);
        }
        direccionClienteRepository.save(direccion);
        return direccion.getIdDireccion();
    }

    @Override
    public DireccionClienteResponse obtenerPorId(Integer id) {
        DireccionCliente direccion = direccionClienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
        return mapToResponse(direccion);
    }

    @Override
    public DireccionClienteResponse actualizar(Integer id, DireccionClienteRequest request) {
        DireccionCliente direccion = direccionClienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
        direccion.setDireccion(request.getDireccion());
        direccion.setReferencia(request.getReferencia());
        direccion.setDistrito(request.getDistrito());
        Cliente cliente = clienteRepository.findById(request.getIdCliente())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        direccion.setCliente(cliente);
        if (Boolean.TRUE.equals(request.getPredeterminada())) {
            desmarcarOtrasPredeterminadas(request.getIdCliente());
            direccion.setPredeterminada(true);
        } else {
            direccion.setPredeterminada(false);
        }
        direccionClienteRepository.save(direccion);
        return mapToResponse(direccion);
    }

    @Override
    public void eliminar(Integer id) {
        direccionClienteRepository.deleteById(id);
    }

    @Override
    public List<DireccionClienteResponse> listarTodos() {
        return direccionClienteRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<DireccionClienteResponse> listarPorCliente(Integer idCliente) {
        List<DireccionCliente> direcciones = direccionClienteRepository.findAll().stream()
            .filter(d -> d.getCliente() != null && d.getCliente().getId().equals(idCliente))
            .collect(Collectors.toList());
        return direcciones.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DireccionClienteResponse marcarComoPredeterminada(Integer id) {
        DireccionCliente direccion = direccionClienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        desmarcarOtrasPredeterminadas(direccion.getCliente().getId());
        direccion.setPredeterminada(true);
        direccionClienteRepository.save(direccion);

        return mapToResponse(direccion);
    }

    private void desmarcarOtrasPredeterminadas(Integer idCliente) {
        List<DireccionCliente> direcciones = direccionClienteRepository.findByClienteIdAndPredeterminadaTrue(idCliente);
        for (DireccionCliente d : direcciones) {
            d.setPredeterminada(false);
        }
        direccionClienteRepository.saveAll(direcciones);
    }

    private DireccionClienteResponse mapToResponse(DireccionCliente direccion) {
        DireccionClienteResponse response = new DireccionClienteResponse();
        response.setIdDireccion(direccion.getIdDireccion());
        response.setDireccion(direccion.getDireccion());
        response.setReferencia(direccion.getReferencia());
        response.setDistrito(direccion.getDistrito());
        if (direccion.getCliente() != null) {
            response.setIdCliente(direccion.getCliente().getId());
            response.setNombreCliente(direccion.getCliente().getNombreCompleto());
        }
        response.setPredeterminada(direccion.isPredeterminada());
        return response;
    }
}
