package org.urban.springbootcafeteria.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MetodoEntregaRequest {
    private String nombre;
    private BigDecimal costo;
}
