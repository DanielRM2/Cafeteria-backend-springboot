package org.urban.springbootcafeteria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.urban.springbootcafeteria.dto.response.ProductoResponse;
import org.urban.springbootcafeteria.dto.request.ProductoRequest;
import org.urban.springbootcafeteria.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos del menú")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(
            summary = "Listar productos por categoría",
            description = "Devuelve una lista de productos ordenados por categoría"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar-por-categoria")
    public List<ProductoResponse> listarProductosOrdenadosPorCategoria() {
        return productoService.listarProductosOrdenadosPorCategoria();
    }

    @Operation(
            summary = "Listar productos populares",
            description = "Devuelve una lista de los productos más populares (top del menú)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos populares obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar-populares")
    public List<ProductoResponse> listarPrimerosProductos() {
        return productoService.listarPrimerosProductos();
    }

    @Operation(summary = "Crear producto", description = "Crea un nuevo producto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping(value = "", consumes = "multipart/form-data")
    public ResponseEntity<ProductoResponse> crear(
            @RequestParam("nombre") String nombre,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam("precio") BigDecimal precio,
            @RequestParam("idCategoriaProducto") Integer idCategoriaProducto,
            @RequestParam("stock") Integer stock,
            @RequestParam("imagen") MultipartFile imagenFile
    ) {
        String nombreArchivo = System.currentTimeMillis() + "_" + imagenFile.getOriginalFilename();
        Path ruta = Paths.get("uploads", nombreArchivo);
        try {
            Files.createDirectories(ruta.getParent());
            imagenFile.transferTo(ruta);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen");
        }
        ProductoRequest request = new ProductoRequest();
        request.setNombre(nombre);
        request.setDescripcion(descripcion);
        request.setPrecio(precio);
        request.setIdCategoriaProducto(idCategoriaProducto);
        request.setStock(stock);
        request.setImagen(nombreArchivo);
        ProductoResponse response = productoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar todos los productos", description = "Obtiene todos los productos")
    @ApiResponse(responseCode = "200", description = "Lista de productos")
    @GetMapping("/listar")
    public List<ProductoResponse> listarTodos() {
        return productoService.listarTodos();
    }

    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable Integer id,
            @RequestParam("nombre") String nombre,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam("precio") BigDecimal precio,
            @RequestParam("idCategoriaProducto") Integer idCategoriaProducto,
            @RequestParam("stock") Integer stock,
            @RequestParam(value = "imagen", required = false) MultipartFile imagenFile
    ) {
        String nombreArchivo = null;
        if (imagenFile != null && !imagenFile.isEmpty()) {
            nombreArchivo = System.currentTimeMillis() + "_" + imagenFile.getOriginalFilename();
            Path ruta = Paths.get("uploads", nombreArchivo);
            try {
                Files.createDirectories(ruta.getParent());
                imagenFile.transferTo(ruta);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la imagen");
            }
        }

        // 1. Obtener el producto actual para mantener la imagen si no se sube una nueva
        ProductoResponse productoActual = productoService.obtenerPorId(id);

        ProductoRequest request = new ProductoRequest();
        request.setNombre(nombre);
        request.setDescripcion(descripcion);
        request.setPrecio(precio);
        request.setIdCategoriaProducto(idCategoriaProducto);
        request.setStock(stock);

        // 2. Lógica para imagen
        if (nombreArchivo != null) {
            request.setImagen(nombreArchivo); // Nueva imagen
        } else if (productoActual != null) {
            request.setImagen(productoActual.getImagen()); // Mantener imagen anterior
        }

        ProductoResponse response = productoService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
