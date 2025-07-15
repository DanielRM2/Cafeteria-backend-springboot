package org.urban.springbootcafeteria.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class VentaRequest {
    @NotNull(message = "El id del pedido es obligatorio")
    private Integer idPedido;
    @NotNull(message = "El total es obligatorio")
    private BigDecimal total;
    // La fecha y el estado se asignan automáticamente en el backend
}
