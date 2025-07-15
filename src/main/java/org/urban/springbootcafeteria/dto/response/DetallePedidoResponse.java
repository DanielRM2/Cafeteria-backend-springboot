package org.urban.springbootcafeteria.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetallePedidoResponse {
    private Integer idDetallePedido;
    private Integer idPedido;
    private Integer idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
