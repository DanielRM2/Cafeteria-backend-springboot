package org.urban.springbootcafeteria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.urban.springbootcafeteria.dto.request.CategoriaProductoRequest;
import org.urban.springbootcafeteria.dto.response.CategoriaProductoResponse;
import org.urban.springbootcafeteria.service.CategoriaProductoService;
import java.util.List;

@RestController
@RequestMapping("/api/categorias-producto")
@Tag(name = "Categorías de Producto", description = "Operaciones CRUD para categorías de producto")
@Validated
public class CategoriaProductoController {
    @Autowired
    private CategoriaProductoService categoriaProductoService;

    @Operation(summary = "Crear categoría", description = "Crea una nueva categoría de producto")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Categoría creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("")
    public ResponseEntity<CategoriaProductoResponse> crear(@Valid @RequestBody CategoriaProductoRequest request) {
        Integer id = categoriaProductoService.crear(request);
        CategoriaProductoResponse response = categoriaProductoService.obtenerPorId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener categoría por ID", description = "Obtiene una categoría por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProductoResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaProductoService.obtenerPorId(id));
    }

    @Operation(summary = "Listar todas las categorías", description = "Obtiene todas las categorías de producto")
    @ApiResponse(responseCode = "200", description = "Lista de categorías")
    @GetMapping("/listar")
    public List<CategoriaProductoResponse> listarTodos() {
        return categoriaProductoService.listarTodos();
    }

    @Operation(summary = "Actualizar categoría", description = "Actualiza los datos de una categoría de producto")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoría actualizada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProductoResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody CategoriaProductoRequest request) {
        return ResponseEntity.ok(categoriaProductoService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría de producto por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Categoría eliminada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        categoriaProductoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
