package org.urban.springbootcafeteria.dto.response;

import lombok.Data;

@Data
public class StaffResponse {
    private Integer id;
    private String nombreCompleto;
    private String correo;
    private String rol;
    private boolean activo;
}
