package org.urban.springbootcafeteria.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MetodoEntregaResponse {
    private Integer idMetodoEntrega;
    private String nombre;
    private BigDecimal costo;
}
