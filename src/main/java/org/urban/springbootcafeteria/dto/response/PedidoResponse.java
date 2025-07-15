package org.urban.springbootcafeteria.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PedidoResponse {
    private Integer idPedido;
    private Integer idCliente;
    private String nombreCliente;
    private Integer idMetodoEntrega;
    private String metodoEntrega;
    private Integer idDireccion;
    private String direccion;
    private Date fechaPedido;
    private Date fechaEntrega;
    private String estado;
    private BigDecimal total;
    private PagoResponse pago;
    private List<DetallePedidoResponse> detalles;
}
