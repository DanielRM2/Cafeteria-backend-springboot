package org.urban.springbootcafeteria.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.urban.springbootcafeteria.validation.OnCreate;
import org.urban.springbootcafeteria.validation.OnUpdate;
import jakarta.validation.constraints.*;

@Data
public class StaffRequest {
    @NotBlank(message = "El nombre es obligatorio", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres", groups = {OnCreate.class, OnUpdate.class})
    private String nombreCompleto;

    @NotBlank(message = "El correo electrónico es obligatorio", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "El correo no tiene un formato válido", groups = {OnCreate.class, OnUpdate.class})
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria", groups = OnCreate.class)
    @Size(min = 5, message = "La contraseña debe tener al menos 5 caracteres", groups = OnCreate.class)
    private String contrasena;

    private String rol;
}
