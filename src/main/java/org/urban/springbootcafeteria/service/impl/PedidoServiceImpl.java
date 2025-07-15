package org.urban.springbootcafeteria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.dto.request.DetallePedidoRequest;
import org.urban.springbootcafeteria.dto.request.PedidoRequest;
import org.urban.springbootcafeteria.dto.response.DetallePedidoResponse;
import org.urban.springbootcafeteria.dto.response.PedidoResponse;
import org.urban.springbootcafeteria.entitie.*;
import org.urban.springbootcafeteria.repository.*;
import org.urban.springbootcafeteria.service.PedidoService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private MetodoEntregaRepository metodoEntregaRepository;
    @Autowired
    private DireccionClienteRepository direccionRepository;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Integer crear(PedidoRequest request) {
        Pedido pedido = new Pedido();
        pedido.setFechaPedido(request.getFechaPedido());
        pedido.setEstado(request.getEstado());
        pedido.setTotal(request.getTotal());

        Cliente cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        pedido.setCliente(cliente);

        MetodoEntrega metodoEntrega = metodoEntregaRepository.findById(request.getIdMetodoEntrega())
                .orElseThrow(() -> new RuntimeException("Método de entrega no encontrado"));
        pedido.setMetodoEntrega(metodoEntrega);

        // SOLO asignar dirección si el idDireccion NO es null
        if (request.getIdDireccion() != null) {
            DireccionCliente direccion = direccionRepository.findById(request.getIdDireccion())
                    .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));
            pedido.setDireccion(direccion);
        } else {
            pedido.setDireccion(null); // Para recojo en tienda
        }

        pedidoRepository.save(pedido);

        // Guardar detalles del pedido
        if (request.getDetalles() != null) {
            for (DetallePedidoRequest detReq : request.getDetalles()) {
                DetallePedido detalle = new DetallePedido();
                detalle.setCantidad(detReq.getCantidad());
                detalle.setPrecioUnitario(detReq.getPrecioUnitario());
                detalle.setSubtotal(detReq.getSubtotal());
                detalle.setPedido(pedido);
                Producto producto = productoRepository.findById(detReq.getIdProducto())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                detalle.setProducto(producto);
                detallePedidoRepository.save(detalle);
            }
        }

        return pedido.getIdPedido();
    }

    @Override
    public PedidoResponse obtenerPorId(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return mapToResponse(pedido);
    }

    @Override
    public PedidoResponse actualizar(Integer id, PedidoRequest request) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setFechaPedido(request.getFechaPedido());
        pedido.setEstado(request.getEstado());
        Cliente cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        pedido.setCliente(cliente);
        pedidoRepository.save(pedido);
        return mapToResponse(pedido);
    }

    @Override
    public void eliminar(Integer id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public List<PedidoResponse> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponse> listarPorCliente(Integer idCliente) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getCliente() != null && p.getCliente().getId().equals(idCliente))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PedidoResponse mapToResponse(Pedido pedido) {
        PedidoResponse response = new PedidoResponse();
        response.setIdPedido(pedido.getIdPedido());
        response.setFechaPedido(pedido.getFechaPedido());
        response.setEstado(pedido.getEstado());
        response.setTotal(pedido.getTotal());
        response.setIdCliente(pedido.getCliente().getId());
        response.setNombreCliente(pedido.getCliente().getNombreCompleto());
        response.setIdMetodoEntrega(pedido.getMetodoEntrega().getIdMetodoEntrega());
        response.setMetodoEntrega(pedido.getMetodoEntrega().getNombre());

        // Manejo seguro de dirección (puede ser null)
        if (pedido.getDireccion() != null) {
            response.setIdDireccion(pedido.getDireccion().getIdDireccion());
            response.setDireccion(pedido.getDireccion().getDireccion());
        } else {
            response.setIdDireccion(null);
            response.setDireccion(null);
        }

        // Mapeo de detalles
        List<DetallePedido> detalles = detallePedidoRepository.findByPedido(pedido);
        List<DetallePedidoResponse> detallesResp = detalles.stream()
                .map(this::mapDetalleToResponse)
                .collect(Collectors.toList());
        response.setDetalles(detallesResp);

        return response;
    }

    private DetallePedidoResponse mapDetalleToResponse(DetallePedido detalle) {
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