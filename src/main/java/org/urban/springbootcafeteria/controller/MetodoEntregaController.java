package org.urban.springbootcafeteria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.urban.springbootcafeteria.dto.request.MetodoEntregaRequest;
import org.urban.springbootcafeteria.dto.response.MetodoEntregaResponse;
import org.urban.springbootcafeteria.service.MetodoEntregaService;

import java.util.List;

@RestController
@RequestMapping("/api/metodosEntrega")
@Tag(name = "Métodos de Entrega", description = "Operaciones relacionadas con los métodos de entrega disponibles")
public class MetodoEntregaController {

    @Autowired
    private MetodoEntregaService metodoEntregaService;

    @Operation(
            summary = "Listar todos los métodos de entrega",
            description = "Obtiene una lista de todos los métodos de entrega disponibles"
    )
    @GetMapping("/listar")
    public List<MetodoEntregaResponse> listarTodos() {
        return metodoEntregaService.listarTodos();
    }

    @Operation(summary = "Crear método de entrega", description = "Crea un nuevo método de entrega")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Método de entrega creado correctamente",
                content = @Content(schema = @Schema(implementation = MetodoEntregaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("")
    public ResponseEntity<MetodoEntregaResponse> crear(@Valid @RequestBody MetodoEntregaRequest request) {
        MetodoEntregaResponse response = metodoEntregaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener método de entrega por ID", description = "Obtiene un método de entrega por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Método de entrega encontrado",
                content = @Content(schema = @Schema(implementation = MetodoEntregaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Método de entrega no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MetodoEntregaResponse> obtenerPorId(@Parameter(in = ParameterIn.PATH, description = "ID del método de entrega", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(metodoEntregaService.obtenerPorId(id));
    }

    @Operation(summary = "Actualizar método de entrega", description = "Actualiza los datos de un método de entrega")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Método de entrega actualizado",
                content = @Content(schema = @Schema(implementation = MetodoEntregaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Método de entrega no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MetodoEntregaResponse> actualizar(@Parameter(in = ParameterIn.PATH, description = "ID del método de entrega", required = true) @PathVariable Integer id, @Valid @RequestBody MetodoEntregaRequest request) {
        return ResponseEntity.ok(metodoEntregaService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar método de entrega", description = "Elimina un método de entrega por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Método de entrega eliminado"),
        @ApiResponse(responseCode = "404", description = "Método de entrega no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(in = ParameterIn.PATH, description = "ID del método de entrega", required = true) @PathVariable Integer id) {
        metodoEntregaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
