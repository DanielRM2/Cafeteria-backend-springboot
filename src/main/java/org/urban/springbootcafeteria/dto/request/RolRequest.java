package org.urban.springbootcafeteria.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class RolRequest {
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 20, message = "El nombre del rol no puede tener más de 20 caracteres")
    private String nombre;
}
