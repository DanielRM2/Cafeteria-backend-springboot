package org.urban.springbootcafeteria.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
public class DetallePedidoRequest {
    @NotNull(message = "El id del pedido es obligatorio")
    private Integer idPedido;
    @NotNull(message = "El id del producto es obligatorio")
    private Integer idProducto;
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio unitario debe ser mayor que 0")
    private BigDecimal precioUnitario;
    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.01", message = "El subtotal debe ser mayor que 0")
    private BigDecimal subtotal;
}
