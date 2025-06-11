package org.urban.springbootcafeteria.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.urban.springbootcafeteria.Dto.MetodoEntregaResponse;
import org.urban.springbootcafeteria.Service.MetodoEntregaService;

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
}
