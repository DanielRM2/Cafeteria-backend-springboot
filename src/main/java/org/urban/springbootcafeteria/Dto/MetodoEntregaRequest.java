package org.urban.springbootcafeteria.Dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MetodoEntregaRequest {
    private String nombre;
    private BigDecimal costo;
}
