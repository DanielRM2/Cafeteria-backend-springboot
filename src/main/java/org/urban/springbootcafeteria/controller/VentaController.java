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
import org.urban.springbootcafeteria.dto.request.VentaRequest;
import org.urban.springbootcafeteria.dto.response.VentaResponse;
import org.urban.springbootcafeteria.service.VentaService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@Tag(name = "Ventas", description = "Operaciones CRUD para ventas")
@Validated
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @Operation(summary = "Crear venta", description = "Crea una nueva venta")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Venta creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("")
    public ResponseEntity<VentaResponse> crear(@Valid @RequestBody VentaRequest request) {
        Integer id = ventaService.crear(request);
        VentaResponse response = ventaService.obtenerPorId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener venta por ID", description = "Obtiene una venta por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta encontrada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @Operation(summary = "Listar todas las ventas", description = "Obtiene todas las ventas")
    @ApiResponse(responseCode = "200", description = "Lista de ventas")
    @GetMapping("/listar")
    public List<VentaResponse> listarTodos() {
        return ventaService.listarTodos();
    }

    @Operation(summary = "Listar ventas por cliente", description = "Obtiene todas las ventas de un cliente")
    @ApiResponse(responseCode = "200", description = "Lista de ventas del cliente")
    @GetMapping("/cliente/{idCliente}")
    public List<VentaResponse> listarPorCliente(@PathVariable Integer idCliente) {
        return ventaService.listarPorCliente(idCliente);
    }

    @Operation(summary = "Actualizar venta", description = "Actualiza los datos de una venta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Venta actualizada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VentaResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody VentaRequest request) {
        return ResponseEntity.ok(ventaService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar venta", description = "Elimina una venta por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Venta eliminada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
