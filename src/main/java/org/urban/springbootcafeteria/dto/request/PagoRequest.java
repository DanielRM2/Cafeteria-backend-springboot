package org.urban.springbootcafeteria.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
public class PagoRequest {
    @NotNull(message = "El id del pedido es obligatorio")
    private Integer idPedido;
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que 0")
    private BigDecimal monto;
    // Estos campos serán llenados/actualizados por el backend tras interactuar con Mercado Pago
    private String mercadoPagoId;
    private String mercadoPagoStatus;
    private String paymentLink;
    private String payerEmail;
    private String estado;
}
