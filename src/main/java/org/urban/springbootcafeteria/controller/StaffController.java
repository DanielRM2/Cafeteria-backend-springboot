package org.urban.springbootcafeteria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urban.springbootcafeteria.dto.request.StaffRequest;
import org.urban.springbootcafeteria.dto.request.LoginRequest;
import org.urban.springbootcafeteria.dto.response.AuthStaffResponse;
import org.urban.springbootcafeteria.dto.response.StaffResponse;
import org.urban.springbootcafeteria.dto.response.ErrorResponse;
import org.urban.springbootcafeteria.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/staff")
@Tag(name = "Staff", description = "Operaciones relacionadas con el personal de la cafetería")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Operation(summary = "Registrar un nuevo staff", description = "Crea una nueva cuenta de staff con la información proporcionada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Staff registrado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "409", description = "El correo electrónico ya está en uso")
    })
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody StaffRequest request) {
        try {
            String mensaje = staffService.registrar(request);
            return ResponseEntity.ok(Map.of("message", mensaje));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error en el registro",
                e.getMessage(),
                "/api/staff/registro"
            );
            return ResponseEntity.badRequest().body(error);
        }
    }

    @Operation(summary = "Iniciar sesión de staff", description = "Autentica a un staff y devuelve un token JWT")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
        @ApiResponse(responseCode = "404", description = "Staff no encontrado")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthStaffResponse response = staffService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Error de autenticación",
                e.getMessage(),
                "/api/staff/login"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    @Operation(summary = "Obtener staff por ID", description = "Obtiene un staff por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Staff encontrado"),
        @ApiResponse(responseCode = "404", description = "Staff no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            StaffResponse response = staffService.obtenerPorId(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Staff no encontrado",
                e.getMessage(),
                "/api/staff/" + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @Operation(summary = "Actualizar staff", description = "Actualiza los datos de un staff")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Staff actualizado"),
        @ApiResponse(responseCode = "404", description = "Staff no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody StaffRequest request) {
        try {
            StaffResponse response = staffService.actualizar(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error al actualizar staff",
                e.getMessage(),
                "/api/staff/" + id
            );
            return ResponseEntity.badRequest().body(error);
        }
    }

    @Operation(summary = "Eliminar staff", description = "Elimina un staff por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Staff eliminado"),
        @ApiResponse(responseCode = "404", description = "Staff no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        staffService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cambiar estado de staff", description = "Bloquea o desbloquea un staff")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado del staff actualizado"),
        @ApiResponse(responseCode = "404", description = "Staff no encontrado")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<StaffResponse> cambiarEstado(@PathVariable Integer id, @RequestParam boolean activo) {
        staffService.cambiarEstado(id, activo);
        StaffResponse response = staffService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar staff", description = "Obtiene todos los staff")
    @ApiResponse(responseCode = "200", description = "Lista de staff")
    @GetMapping("/listar")
    public ResponseEntity<?> listarTodos() {
        try {
            List<StaffResponse> lista = staffService.listarTodos();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "No se encontraron staffs",
                e.getMessage(),
                "/api/staff/listar"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
