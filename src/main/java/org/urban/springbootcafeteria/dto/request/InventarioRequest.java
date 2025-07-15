package org.urban.springbootcafeteria.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventarioRequest {
    private Integer idInventario;
    
    @NotNull(message = "El ID de producto es obligatorio")
    private Integer idProducto;
    
    @Min(value = 0, message = "La cantidad disponible no puede ser negativa")
    @NotNull(message = "La cantidad disponible es obligatoria")
    private Integer cantidadDisponible;
}
