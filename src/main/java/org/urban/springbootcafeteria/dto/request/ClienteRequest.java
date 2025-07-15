package org.urban.springbootcafeteria.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.urban.springbootcafeteria.validation.OnCreate;
import org.urban.springbootcafeteria.validation.OnUpdate;

@Data
public class ClienteRequest {

    @NotBlank(message = "El nombre es obligatorio", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres", groups = {OnCreate.class, OnUpdate.class})
    private String nombreCompleto;

    @NotBlank(message = "El correo electrónico es obligatorio", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "El correo no tiene un formato válido", groups = {OnCreate.class, OnUpdate.class})
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria", groups = OnCreate.class)
    @Size(min = 5, message = "La contraseña debe tener al menos 5 caracteres", groups = OnCreate.class)
    private String contrasena;

    @NotBlank(message = "El teléfono es obligatorio", groups = {OnCreate.class, OnUpdate.class})
    @Pattern(regexp = "^9\\d{8}$", message = "El número de celular debe comenzar con 9 y tener 9 dígitos", groups = {OnCreate.class, OnUpdate.class})
    private String telefono;
}
