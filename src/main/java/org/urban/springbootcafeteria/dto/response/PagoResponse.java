package org.urban.springbootcafeteria.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagoResponse {
    private Integer idPago;
    private Integer idPedido;
    private BigDecimal monto;
    private String estado;
    private String mercadoPagoId;
    private String mercadoPagoStatus;
    private String paymentLink;
    private String payerEmail;
    private LocalDateTime fechaPago;
}
