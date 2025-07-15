package org.urban.springbootcafeteria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.dto.request.InventarioRequest;
import org.urban.springbootcafeteria.dto.response.InventarioResponse;
import org.urban.springbootcafeteria.entitie.Inventario;
import org.urban.springbootcafeteria.repository.InventarioRepository;
import org.urban.springbootcafeteria.service.InventarioService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;

    @Autowired
    public InventarioServiceImpl(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public InventarioResponse agregarInventario(InventarioRequest inventarioRequest) {
        Inventario inventario = new Inventario();
        inventario.setCantidadDisponible(inventarioRequest.getCantidadDisponible());
        // Set other fields as necessary
        Inventario inventarioGuardado = inventarioRepository.save(inventario);
        return convertToResponse(inventarioGuardado);
    }

    @Override
    public List<InventarioResponse> obtenerTodoElInventario() {
        return inventarioRepository.findAll().stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    private InventarioResponse convertToResponse(Inventario inventario) {
        InventarioResponse response = new InventarioResponse();
        response.setIdInventario(inventario.getIdInventario());
        response.setCantidadDisponible(inventario.getCantidadDisponible());
        
        if (inventario.getProducto() != null) {
            response.setIdProducto(inventario.getProducto().getIdProducto());
            response.setNombreProducto(inventario.getProducto().getNombre());
        }
        
        return response;
    }

    @Override
    public InventarioResponse obtenerInventarioPorId(Integer id) {
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con id: " + id));
        return convertToResponse(inventario);
    }

    @Override
    public InventarioResponse actualizarInventario(Integer id, InventarioRequest inventarioRequest) {
        Inventario inventarioExistente = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con id: " + id));

        inventarioExistente.setCantidadDisponible(inventarioRequest.getCantidadDisponible());
        // Actualizar otros campos según sea necesario

        Inventario inventarioActualizado = inventarioRepository.save(inventarioExistente);
        return convertToResponse(inventarioActualizado);
    }

    @Override
    public void eliminarInventario(Integer id) {
        inventarioRepository.deleteById(id);
    }

    public InventarioResponse actualizarStock(Integer id, Integer cantidad) {
        Inventario inventario = inventarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Inventario no encontrado con id: " + id));

        // Calculate new stock (assuming 'cantidad' is a delta)
        int nuevoStock = inventario.getCantidadDisponible() + cantidad;
        if (nuevoStock < 0) {
            throw new IllegalStateException("No se puede reducir el stock por debajo de cero");
        }

        inventario.setCantidadDisponible(nuevoStock);
        Inventario inventarioActualizado = inventarioRepository.save(inventario);
        return convertToResponse(inventarioActualizado);
    }

    public List<InventarioResponse> obtenerInventario() {
        return inventarioRepository.findAllWithProductos().stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
}
