package org.urban.springbootcafeteria.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VentaResponse {
    private Integer idVenta;
    private Integer idPedido;
    private BigDecimal total;
    private LocalDateTime fechaVenta;
    private String estado;
}
