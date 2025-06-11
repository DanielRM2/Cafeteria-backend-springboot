package org.urban.springbootcafeteria.Entitie;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

@Entity
@Table(name = "DetallePedido")
@Data
public class DetallePedido {

    // Identificador único del detalle del pedido
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetallePedido;

    // Pedido al que pertenece este detalle
    @ManyToOne
    @JoinColumn(name = "idPedido", nullable = false)
    @NotNull(message = "El pedido no puede ser nulo")
    private Pedido pedido;

    // Producto al que se refiere este detalle
    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    @NotNull(message = "El producto no puede ser nulo")
    private Producto producto;

    // Cantidad del producto en este detalle
    @Column(nullable = false)
    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    // Precio unitario del producto en este detalle
    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "El precio unitario no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio unitario debe ser mayor que 0")
    private BigDecimal precioUnitario;

    // Subtotal de este detalle (cantidad * precioUnitario)
    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "El subtotal no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El subtotal debe ser mayor que 0")
    private BigDecimal subtotal;

}
