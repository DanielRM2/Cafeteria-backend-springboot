package org.urban.springbootcafeteria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urban.springbootcafeteria.dto.request.ClienteRequest;
import org.urban.springbootcafeteria.dto.request.LoginRequest;
import org.urban.springbootcafeteria.dto.response.AuthClienteResponse;
import org.urban.springbootcafeteria.dto.response.ClienteResponse;
import org.urban.springbootcafeteria.dto.response.ErrorResponse;
import org.urban.springbootcafeteria.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cliente")
@Tag(name = "Cliente", description = "Operaciones relacionadas con los clientes de la cafetería")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(
            summary = "Registrar un nuevo cliente",
            description = "Crea una nueva cuenta de cliente con la información proporcionada"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "El correo electrónico ya está en uso")
    })
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody ClienteRequest request) {
        try {
            String mensaje = clienteService.registrar(request);
            return ResponseEntity.ok(Map.of("message", mensaje));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error en el registro",
                e.getMessage(),
                "/api/cliente/registro"
            );
            return ResponseEntity.badRequest().body(error);
        }
    }

    @Operation(
            summary = "Iniciar sesión de cliente",
            description = "Autentica a un cliente y devuelve un token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthClienteResponse response = clienteService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Error de autenticación",
                e.getMessage(),
                "/api/cliente/login"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    @Operation(
            summary = "Saludo de prueba para clientes autenticados",
            description = "Endpoint de prueba que requiere autenticación de cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensaje de saludo"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado - Se requiere autenticación de cliente")
    })
    @GetMapping("/hola")
    public String holaCliente() {
        return "¡Hola cliente autenticado! 👋";
    }

    // Eliminado el método crear porque /registro lo cubre

    @Operation(summary = "Listar clientes", description = "Obtiene todos los clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes")
    @GetMapping("/listar")
    public List<ClienteResponse> listarTodos() {
        return clienteService.listarTodos();
    }

    @Operation(summary = "Obtener cliente por ID", description = "Obtiene un cliente por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody ClienteRequest request) {
        try {
            ClienteResponse response = clienteService.actualizar(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Cambiar estado de cliente", description = "Bloquea o desbloquea un cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado del cliente actualizado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<ClienteResponse> cambiarEstado(@PathVariable Integer id, @RequestParam boolean activo) {
        clienteService.cambiarEstado(id, activo);
        ClienteResponse response = clienteService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente eliminado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
