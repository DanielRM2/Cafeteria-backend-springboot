package org.urban.springbootcafeteria.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class CategoriaProductoRequest {
    @NotBlank(message = "El nombre de la categoría no puede estar en blanco")
    @Size(max = 100, message = "El nombre de la categoría no puede tener más de 100 caracteres")
    private String nombreCategoria;
}
