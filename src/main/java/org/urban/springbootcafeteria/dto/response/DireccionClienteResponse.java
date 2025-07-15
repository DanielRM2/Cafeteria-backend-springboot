package org.urban.springbootcafeteria.dto.response;

import lombok.Data;

@Data
public class DireccionClienteResponse {
    private Integer idDireccion;
    private Integer idCliente;
    private String nombreCliente;
    private String direccion;
    private String referencia;
    private String distrito;
    private Boolean predeterminada;
}
