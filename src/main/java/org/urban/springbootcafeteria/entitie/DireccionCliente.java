package org.urban.springbootcafeteria.entitie;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "DireccionCliente")
@Data
public class DireccionCliente {

    // Identificador único de la dirección del cliente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDireccion;

    // Cliente al que pertenece esta dirección
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    @NotNull(message = "Debe seleccionar un cliente")
    @JsonBackReference("cliente-direcciones")
    private Cliente cliente;

    // Dirección del cliente
    @Column(nullable = false, length = 255)
    @NotBlank(message = "La dirección no puede estar en blanco")
    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    private String direccion;

    // Referencia para encontrar la dirección (opcional)
    @Column(length = 100)
    @Size(max = 100, message = "La referencia no puede tener más de 100 caracteres")
    private String referencia;

    // Distrito donde se encuentra la dirección
    @Column(length = 50)
    @Size(max = 50, message = "El distrito no puede tener más de 50 caracteres")
    private String distrito;

    // Indica si esta dirección es la predeterminada
    @Column(nullable = false)
    private boolean predeterminada;

}
