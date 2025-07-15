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
import org.urban.springbootcafeteria.dto.request.DireccionClienteRequest;
import org.urban.springbootcafeteria.dto.response.DireccionClienteResponse;
import org.urban.springbootcafeteria.service.DireccionClienteService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/direcciones-cliente")
@Tag(name = "Direcciones de Cliente", description = "Operaciones CRUD para direcciones de cliente")
@Validated
public class DireccionClienteController {
    @Autowired
    private DireccionClienteService direccionClienteService;

    @Operation(summary = "Crear dirección", description = "Crea una nueva dirección de cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Dirección creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("")
    public ResponseEntity<DireccionClienteResponse> crear(@Valid @RequestBody DireccionClienteRequest request) {
        Integer id = direccionClienteService.crear(request);
        DireccionClienteResponse response = direccionClienteService.obtenerPorId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener dirección por ID", description = "Obtiene una dirección de cliente por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dirección encontrada"),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DireccionClienteResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(direccionClienteService.obtenerPorId(id));
    }

    @Operation(summary = "Listar todas las direcciones", description = "Obtiene todas las direcciones de cliente")
    @ApiResponse(responseCode = "200", description = "Lista de direcciones")
    @GetMapping("/listar")
    public List<DireccionClienteResponse> listarTodos() {
        return direccionClienteService.listarTodos();
    }

    @Operation(summary = "Listar direcciones por cliente", description = "Obtiene todas las direcciones de un cliente por su idCliente")
    @ApiResponse(responseCode = "200", description = "Lista de direcciones del cliente")
    @GetMapping("/cliente/{idCliente}")
    public List<DireccionClienteResponse> listarPorCliente(@PathVariable Integer idCliente) {
        return direccionClienteService.listarPorCliente(idCliente);
    }

    @Operation(summary = "Actualizar dirección", description = "Actualiza los datos de una dirección de cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dirección actualizada"),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DireccionClienteResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody DireccionClienteRequest request) {
        return ResponseEntity.ok(direccionClienteService.actualizar(id, request));
    }

    @Operation(summary = "Marcar dirección como predeterminada", description = "Marca una dirección de cliente como predeterminada y devuelve la dirección actualizada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dirección marcada como predeterminada"),
            @ApiResponse(responseCode = "404", description = "Dirección no encontrada")
    })
    @PutMapping("/{id}/predeterminada")
    public ResponseEntity<DireccionClienteResponse> marcarComoPredeterminada(@PathVariable Integer id) {
        DireccionClienteResponse response = direccionClienteService.marcarComoPredeterminada(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar dirección", description = "Elimina una dirección de cliente por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Dirección eliminada"),
        @ApiResponse(responseCode = "404", description = "Dirección no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        direccionClienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
