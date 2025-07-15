package org.urban.springbootcafeteria.service.impl;

import org.urban.springbootcafeteria.dto.request.VentaRequest;
import org.urban.springbootcafeteria.dto.response.VentaResponse;
import org.urban.springbootcafeteria.entitie.Venta;
import org.urban.springbootcafeteria.entitie.Pedido;
import org.urban.springbootcafeteria.entitie.Cliente;
import org.urban.springbootcafeteria.repository.VentaRepository;
import org.urban.springbootcafeteria.repository.PedidoRepository;
import org.urban.springbootcafeteria.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Integer crear(VentaRequest request) {
        Pedido pedido = pedidoRepository.findById(request.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Venta venta = new Venta();
        venta.setPedido(pedido);
        venta.setTotal(request.getTotal());
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstado(Venta.EstadoVenta.FINALIZADA);

        ventaRepository.save(venta);
        return venta.getIdVenta();
    }

    @Override
    public VentaResponse obtenerPorId(Integer id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        return mapToResponse(venta);
    }

    @Override
    public VentaResponse actualizar(Integer id, VentaRequest request) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        Pedido pedido = pedidoRepository.findById(request.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        venta.setPedido(pedido);
        venta.setTotal(request.getTotal());
        // No actualizamos fechaVenta ni estado aquí, pero podrías hacerlo si lo necesitas
        ventaRepository.save(venta);
        return mapToResponse(venta);
    }

    @Override
    public void eliminar(Integer id) {
        ventaRepository.deleteById(id);
    }

    @Override
    public List<VentaResponse> listarTodos() {
        return ventaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VentaResponse> listarPorCliente(Integer idCliente) {
        return ventaRepository.findAll().stream()
                .filter(v -> v.getPedido() != null && v.getPedido().getCliente() != null && v.getPedido().getCliente().getId().equals(idCliente))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private VentaResponse mapToResponse(Venta venta) {
        VentaResponse response = new VentaResponse();
        response.setIdVenta(venta.getIdVenta());
        response.setIdPedido(venta.getPedido() != null ? venta.getPedido().getIdPedido() : null);
        response.setTotal(venta.getTotal());
        response.setFechaVenta(venta.getFechaVenta());
        response.setEstado(venta.getEstado() != null ? venta.getEstado().name() : null);
        return response;
    }
}
