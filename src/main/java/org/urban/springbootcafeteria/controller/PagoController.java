package org.urban.springbootcafeteria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.urban.springbootcafeteria.dto.request.PagoRequest;
import org.urban.springbootcafeteria.dto.response.PagoResponse;
import org.urban.springbootcafeteria.dto.response.PedidoResponse;
import org.urban.springbootcafeteria.entitie.Pago;
import org.urban.springbootcafeteria.entitie.Pedido;
import org.urban.springbootcafeteria.service.PagoService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@Tag(name = "Pagos", description = "Operaciones CRUD para pagos")
@Validated
public class PagoController {
    @Autowired
    private PagoService pagoService;

    @Operation(summary = "Crear pago", description = "Crea un nuevo pago")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pago creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("")
    public ResponseEntity<PagoResponse> crear(@Valid @RequestBody PagoRequest request) {
        Integer id = pagoService.crear(request);
        PagoResponse response = pagoService.obtenerPorId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener pago por ID", description = "Obtiene un pago por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @Operation(summary = "Listar todos los pagos", description = "Obtiene todos los pagos")
    @ApiResponse(responseCode = "200", description = "Lista de pagos")
    @GetMapping("/listar")
    public List<PagoResponse> listarTodos() {
        return pagoService.listarTodos();
    }

    @Operation(summary = "Listar pagos por cliente", description = "Obtiene todos los pagos de un cliente")
    @ApiResponse(responseCode = "200", description = "Lista de pagos del cliente")
    @GetMapping("/cliente/{idCliente}")
    public List<PagoResponse> listarPorCliente(@PathVariable Integer idCliente) {
        return pagoService.listarPorCliente(idCliente);
    }

    @Operation(summary = "Actualizar pago", description = "Actualiza los datos de un pago")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago actualizado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PagoResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody PagoRequest request) {
        return ResponseEntity.ok(pagoService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar pago", description = "Elimina un pago por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pago eliminado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/preferencia/{preferenceId}/pedido")
    public ResponseEntity<PedidoResponse> obtenerPedidoPorPreferenceId(@PathVariable String preferenceId) {
        Pago pago = pagoService.obtenerPorPreferenceId(preferenceId);
        Pedido pedido = pago.getPedido();

        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        PedidoResponse response = new PedidoResponse();
        response.setIdPedido(pedido.getIdPedido());
        response.setEstado(pedido.getEstado());
        // Mapeo seguro para cliente
        if (pedido.getCliente() != null) {
            response.setIdCliente(pedido.getCliente().getId());
            response.setNombreCliente(pedido.getCliente().getNombreCompleto());
        }
        // Mapeo seguro para metodo de entrega
        if (pedido.getMetodoEntrega() != null) {
            response.setIdMetodoEntrega(pedido.getMetodoEntrega().getIdMetodoEntrega());
            response.setMetodoEntrega(pedido.getMetodoEntrega().getNombre());
        }
        // Mapeo seguro para dirección
        if (pedido.getDireccion() != null) {
            response.setIdDireccion(pedido.getDireccion().getIdDireccion());
            response.setDireccion(pedido.getDireccion().getDireccion());
        }
        response.setFechaPedido(pedido.getFechaPedido());
        response.setFechaEntrega(pedido.getFechaEntrega());
        response.setTotal(pedido.getTotal());
        // Puedes mapear pago y detalles si lo necesitas
        // response.setPago(...);
        // response.setDetalles(...);

        return ResponseEntity.ok(response);
    }
}
