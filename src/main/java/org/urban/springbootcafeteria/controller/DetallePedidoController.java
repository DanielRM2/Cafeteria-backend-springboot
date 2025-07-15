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
import org.urban.springbootcafeteria.dto.request.DetallePedidoRequest;
import org.urban.springbootcafeteria.dto.response.DetallePedidoResponse;
import org.urban.springbootcafeteria.service.DetallePedidoService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/detalles-pedido")
@Tag(name = "Detalles de Pedido", description = "Operaciones CRUD para detalles de pedido")
@Validated
public class DetallePedidoController {
    @Autowired
    private DetallePedidoService detallePedidoService;

    @Operation(summary = "Crear detalle de pedido", description = "Crea un nuevo detalle de pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Detalle creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("")
    public ResponseEntity<DetallePedidoResponse> crear(@Valid @RequestBody DetallePedidoRequest request) {
        Integer id = detallePedidoService.crear(request);
        DetallePedidoResponse response = detallePedidoService.obtenerPorId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener detalle por ID", description = "Obtiene un detalle de pedido por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(detallePedidoService.obtenerPorId(id));
    }

    @Operation(summary = "Listar todos los detalles", description = "Obtiene todos los detalles de pedido")
    @ApiResponse(responseCode = "200", description = "Lista de detalles")
    @GetMapping("/listar")
    public List<DetallePedidoResponse> listarTodos() {
        return detallePedidoService.listarTodos();
    }

    @Operation(summary = "Actualizar detalle", description = "Actualiza los datos de un detalle de pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalle actualizado"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody DetallePedidoRequest request) {
        return ResponseEntity.ok(detallePedidoService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar detalle", description = "Elimina un detalle de pedido por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Detalle eliminado"),
        @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        detallePedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
