package org.urban.springbootcafeteria.Dto;

import lombok.Data;
import java.util.Date;

@Data
public class ClienteRequest {
    private String nombreCompleto;
    private String email;
    private String contrasena;
    private String telefono;
    private Date fechaNacimiento;
}