package org.urban.springbootcafeteria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urban.springbootcafeteria.dto.request.InventarioRequest;
import org.urban.springbootcafeteria.dto.response.InventarioResponse;
import org.urban.springbootcafeteria.service.InventarioService;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "Operaciones para gestionar el inventario de productos")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Operation(summary = "Crear nuevo registro de inventario", description = "Agrega un nuevo producto al inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<InventarioResponse> agregarInventario(@RequestBody InventarioRequest inventarioRequest) {
        InventarioResponse nuevoInventario = inventarioService.agregarInventario(inventarioRequest);
        return new ResponseEntity<>(nuevoInventario, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todo el inventario", description = "Obtiene todos los registros del inventario")
    @ApiResponse(responseCode = "200", description = "Lista de inventario")
    @GetMapping
    public ResponseEntity<List<InventarioResponse>> obtenerTodoElInventario() {
        List<InventarioResponse> inventario = inventarioService.obtenerTodoElInventario();
        return ResponseEntity.ok(inventario);
    }

    @Operation(summary = "Obtener registro por ID", description = "Recupera un producto del inventario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro encontrado"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponse> obtenerInventarioPorId(@PathVariable Integer id) {
        InventarioResponse inventario = inventarioService.obtenerInventarioPorId(id);
        return ResponseEntity.ok(inventario);
    }

    @Operation(summary = "Actualizar registro de inventario", description = "Actualiza la información de un producto en el inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro actualizado"),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<InventarioResponse> actualizarInventario(@PathVariable Integer id, @RequestBody InventarioRequest inventarioRequest) {
        InventarioResponse inventarioActualizado = inventarioService.actualizarInventario(id, inventarioRequest);
        return ResponseEntity.ok(inventarioActualizado);
    }

    @Operation(summary = "Eliminar registro de inventario", description = "Elimina un producto del inventario")
    @ApiResponse(responseCode = "204", description = "Registro eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInventario(@PathVariable Integer id) {
        inventarioService.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleStockNegativo(IllegalStateException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
