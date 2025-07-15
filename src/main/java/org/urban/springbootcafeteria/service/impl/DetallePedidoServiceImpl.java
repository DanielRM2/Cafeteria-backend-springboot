package org.urban.springbootcafeteria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.dto.request.DetallePedidoRequest;
import org.urban.springbootcafeteria.dto.response.DetallePedidoResponse;
import org.urban.springbootcafeteria.entitie.DetallePedido;
import org.urban.springbootcafeteria.entitie.Pedido;
import org.urban.springbootcafeteria.entitie.Producto;
import org.urban.springbootcafeteria.repository.DetallePedidoRepository;
import org.urban.springbootcafeteria.repository.PedidoRepository;
import org.urban.springbootcafeteria.repository.ProductoRepository;
import org.urban.springbootcafeteria.service.DetallePedidoService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Integer crear(DetallePedidoRequest request) {
        DetallePedido detalle = new DetallePedido();
        detalle.setCantidad(request.getCantidad());
        detalle.setPrecioUnitario(request.getPrecioUnitario());
        detalle.setSubtotal(request.getSubtotal());
        Pedido pedido = pedidoRepository.findById(request.getIdPedido())
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Producto producto = productoRepository.findById(request.getIdProducto())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detallePedidoRepository.save(detalle);
        return detalle.getIdDetallePedido();
    }

    @Override
    public DetallePedidoResponse obtenerPorId(Integer id) {
        DetallePedido detalle = detallePedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado"));
        return mapToResponse(detalle);
    }

    @Override
    public DetallePedidoResponse actualizar(Integer id, DetallePedidoRequest request) {
        DetallePedido detalle = detallePedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Detalle de pedido no encontrado"));
        detalle.setCantidad(request.getCantidad());
        detalle.setPrecioUnitario(request.getPrecioUnitario());
        detalle.setSubtotal(request.getSubtotal());
        Pedido pedido = pedidoRepository.findById(request.getIdPedido())
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Producto producto = productoRepository.findById(request.getIdProducto())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detallePedidoRepository.save(detalle);
        return mapToResponse(detalle);
    }

    @Override
    public void eliminar(Integer id) {
        detallePedidoRepository.deleteById(id);
    }

    @Override
    public List<DetallePedidoResponse> listarTodos() {
        return detallePedidoRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private DetallePedidoResponse mapToResponse(DetallePedido detalle) {
        DetallePedidoResponse response = new DetallePedidoResponse();
        response.setIdDetallePedido(detalle.getIdDetallePedido());
        response.setCantidad(detalle.getCantidad());
        response.setPrecioUnitario(detalle.getPrecioUnitario());
        response.setSubtotal(detalle.getSubtotal());
        if (detalle.getPedido() != null) {
            response.setIdPedido(detalle.getPedido().getIdPedido());
        }
        if (detalle.getProducto() != null) {
            response.setIdProducto(detalle.getProducto().getIdProducto());
            response.setNombreProducto(detalle.getProducto().getNombre());
        }
        return response;
    }
}
