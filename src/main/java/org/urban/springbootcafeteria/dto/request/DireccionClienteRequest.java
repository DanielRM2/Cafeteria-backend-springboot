package org.urban.springbootcafeteria.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class DireccionClienteRequest {
    @NotNull(message = "El id del cliente es obligatorio")
    private Integer idCliente;
    @NotBlank(message = "La dirección no puede estar en blanco")
    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    private String direccion;
    @Size(max = 100, message = "La referencia no puede tener más de 100 caracteres")
    private String referencia;
    @Size(max = 50, message = "El distrito no puede tener más de 50 caracteres")
    private String distrito;
    private Boolean predeterminada;
}
