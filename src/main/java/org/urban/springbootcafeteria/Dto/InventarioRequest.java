package org.urban.springbootcafeteria.Dto;

import lombok.Data;

@Data
public class InventarioRequest {
    private Integer idProducto;
    private Integer cantidadDisponible;
}
