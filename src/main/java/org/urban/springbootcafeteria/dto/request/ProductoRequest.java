package org.urban.springbootcafeteria.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoRequest {

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
    private BigDecimal precio;

    private String imagen;

    @NotNull(message = "Debe asignarse una categoría")
    private Integer idCategoriaProducto;

    private Integer stock;
}
