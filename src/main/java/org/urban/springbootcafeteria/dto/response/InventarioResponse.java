package org.urban.springbootcafeteria.dto.response;

import lombok.Data;

@Data
public class InventarioResponse {
    private Integer idInventario;
    private Integer idProducto;
    private String nombreProducto;
    private Integer cantidadDisponible;
}
