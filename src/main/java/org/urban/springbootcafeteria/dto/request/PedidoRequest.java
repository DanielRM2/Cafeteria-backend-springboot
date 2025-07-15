package org.urban.springbootcafeteria.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PedidoRequest {
    @NotNull(message = "El id del cliente es obligatorio")
    private Integer idCliente;
    @NotNull(message = "El id del método de entrega es obligatorio")
    private Integer idMetodoEntrega;
    private Integer idDireccion;
    @NotNull(message = "La lista de detalles no puede ser nula")
    @Size(min = 1, message = "Debe haber al menos un detalle de pedido")
    private List<DetallePedidoRequest> detalles;
    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor que 0")
    private BigDecimal total;
    private Date fechaPedido;
    private String estado;
}
