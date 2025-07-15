package org.urban.springbootcafeteria.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthClienteResponse {
    private String mensaje;
    private String token;
    private String rol;
    private ClienteResponse cliente;
}