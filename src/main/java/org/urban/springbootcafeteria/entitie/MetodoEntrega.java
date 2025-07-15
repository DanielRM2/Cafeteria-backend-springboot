package org.urban.springbootcafeteria.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Entity
@Table(name = "MetodoEntrega")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetodoEntrega {

    // Identificador único del metodo de entrega
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMetodoEntrega;

    // Nombre del metodo de entrega
    @NotBlank(message = "El nombre del método de entrega no puede estar en blanco")
    @Size(max = 50, message = "El nombre del método de entrega no puede tener más de 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    // Costo del metodo de entrega
    @DecimalMin(value = "0.00", message = "El costo del método de entrega no puede ser negativo")
    @Column(precision = 10, scale = 2)
    private BigDecimal costo;


    //Constructor personalizado
    public MetodoEntrega(String nombre, BigDecimal costo) {
        this.nombre = nombre;
        this.costo = costo;
    }

}
