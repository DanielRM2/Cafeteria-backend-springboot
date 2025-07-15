package org.urban.springbootcafeteria.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoResponse {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String imagen;
    private Integer idCategoriaProducto;
    private String nombreCategoria;
    private Integer stock;
}
