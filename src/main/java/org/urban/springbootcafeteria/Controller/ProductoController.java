package org.urban.springbootcafeteria.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.urban.springbootcafeteria.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.urban.springbootcafeteria.Dto.ProductoResponse;

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
}
