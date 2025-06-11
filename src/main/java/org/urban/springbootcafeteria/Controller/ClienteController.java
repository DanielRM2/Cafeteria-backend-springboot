package org.urban.springbootcafeteria.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urban.springbootcafeteria.Dto.ClienteRequest;
import org.urban.springbootcafeteria.Dto.ClienteResponse;
import org.urban.springbootcafeteria.Service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con la gestión de clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Registrar un nuevo cliente", description = "Crea un nuevo cliente en el sistema y devuelve su información")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/registro")
    public ResponseEntity<ClienteResponse> registrarCliente(@RequestBody ClienteRequest clienteRequest) {
        ClienteResponse nuevoCliente = clienteService.registrarCliente(clienteRequest);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @Operation(summary = "Iniciar sesión de cliente", description = "Valida las credenciales y devuelve la información del cliente si son correctas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody ClienteRequest request) {
        if (clienteService.validarCredenciales(request.getEmail(), request.getContrasena())) {
            ClienteResponse cliente = clienteService.obtenerClientePorEmail(request.getEmail());
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    @Operation(summary = "Obtener perfil de cliente", description = "Obtiene la información del perfil de un cliente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}/perfil")
    public ResponseEntity<?> obtenerPerfil(@PathVariable Integer id) {
        ClienteResponse cliente = clienteService.obtenerClientePorId(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
    }

    @Operation(summary = "Actualizar datos de un cliente", description = "Actualiza la información de un cliente dado su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(
            @PathVariable Integer id,
            @RequestBody ClienteRequest clienteRequest) {
        clienteService.actualizarCliente(id, clienteRequest);
        return ResponseEntity.ok().build();
    }
}
