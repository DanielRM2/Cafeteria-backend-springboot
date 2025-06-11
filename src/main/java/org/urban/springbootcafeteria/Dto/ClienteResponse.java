package org.urban.springbootcafeteria.Dto;

import lombok.Data;
import java.util.Date;

@Data
public class ClienteResponse {
    private Integer idCliente;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private Date fechaNacimiento;
    // No se incluye la contraseña en la respuesta por seguridad
}