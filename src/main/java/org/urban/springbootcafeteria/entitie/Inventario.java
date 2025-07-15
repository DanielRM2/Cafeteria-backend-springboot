package org.urban.springbootcafeteria.entitie;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "Inventario")
@Data
public class Inventario {

    // Identificador único del inventario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInventario;

    // Producto asociado al inventario
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto")
    @NotNull(message = "Debe seleccionar un producto")
    private Producto producto;

    // Cantidad disponible del producto en el inventario
    @Column(nullable = false)
    @Min(value = 0, message = "La cantidad disponible no puede ser negativa")
    private Integer cantidadDisponible = 0;

}
