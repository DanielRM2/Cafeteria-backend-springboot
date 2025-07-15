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
import org.urban.springbootcafeteria.dto.request.RolRequest;
import org.urban.springbootcafeteria.dto.response.RolResponse;
import org.urban.springbootcafeteria.service.RolService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Operaciones CRUD para roles")
@Validated
public class RolController {
    @Autowired
    private RolService rolService;

    @Operation(summary = "Crear rol", description = "Crea un nuevo rol")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Rol creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("")
    public ResponseEntity<RolResponse> crear(@Valid @RequestBody RolRequest request) {
        Integer id = rolService.crear(request);
        RolResponse response = rolService.obtenerPorId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener rol por ID", description = "Obtiene un rol por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol encontrado"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RolResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(rolService.obtenerPorId(id));
    }

    @Operation(summary = "Listar todos los roles", description = "Obtiene todos los roles")
    @ApiResponse(responseCode = "200", description = "Lista de roles")
    @GetMapping("/listar")
    public List<RolResponse> listarTodos() {
        return rolService.listarTodos();
    }

    @Operation(summary = "Actualizar rol", description = "Actualiza los datos de un rol")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rol actualizado"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RolResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody RolRequest request) {
        return ResponseEntity.ok(rolService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar rol", description = "Elimina un rol por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Rol eliminado"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        rolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
