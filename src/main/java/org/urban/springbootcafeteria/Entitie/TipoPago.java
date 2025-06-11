package org.urban.springbootcafeteria.Entitie;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TipoPago")
@Data
public class TipoPago {

    // Identificador único del tipo de pago
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoPago;

    // Nombre del tipo de pago (ej: "Tarjeta Debito/Credito", "Yape", "Paypal")
    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String nombre;

}
