package org.urban.springbootcafeteria.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ClienteResponse {
    private Integer id;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String rol;
    private boolean activo; // Nuevo campo para indicar si el cliente está activo o bloqueado

}