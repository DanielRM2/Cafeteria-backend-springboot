package org.urban.springbootcafeteria.Dto;

import lombok.Data;

@Data
public class InventarioResponse {
    private Integer idInventario;
    private Integer idProducto;
    private String nombreProducto;
    private Integer cantidadDisponible;
}
