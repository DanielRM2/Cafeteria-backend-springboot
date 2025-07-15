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
import org.urban.springbootcafeteria.dto.request.PedidoRequest;
import org.urban.springbootcafeteria.dto.response.PedidoResponse;
import org.urban.springbootcafeteria.service.PedidoService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Operaciones CRUD para pedidos")
@Validated
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Crear pedido", description = "Crea un nuevo pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("")
    public ResponseEntity<PedidoResponse> crear(@Valid @RequestBody PedidoRequest request) {
        Integer id = pedidoService.crear(request);
        PedidoResponse response = pedidoService.obtenerPorId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener pedido por ID", description = "Obtiene un pedido por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    @Operation(summary = "Listar todos los pedidos", description = "Obtiene todos los pedidos")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos")
    @GetMapping("/listar")
    public List<PedidoResponse> listarTodos() {
        return pedidoService.listarTodos();
    }

    @Operation(summary = "Listar pedidos por cliente", description = "Obtiene todos los pedidos de un cliente")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos del cliente")
    @GetMapping("/cliente/{idCliente}")
    public List<PedidoResponse> listarPorCliente(@PathVariable Integer idCliente) {
        return pedidoService.listarPorCliente(idCliente);
    }

    @Operation(summary = "Actualizar pedido", description = "Actualiza los datos de un pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido actualizado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody PedidoRequest request) {
        return ResponseEntity.ok(pedidoService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pedido eliminado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
