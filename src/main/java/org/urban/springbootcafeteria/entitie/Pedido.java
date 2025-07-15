package org.urban.springbootcafeteria.entitie;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pedido")
@Data
public class Pedido {

    // Identificador único del pedido
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    // Cliente que realiza el pedido
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    @NotNull(message = "Debe seleccionar un cliente")
    private Cliente cliente;

    // Metodo de entrega del pedido
    @ManyToOne
    @JoinColumn(name = "idMetodoEntrega", nullable = false)
    @NotNull(message = "Debe seleccionar un método de entrega")
    private MetodoEntrega metodoEntrega;

    // Dirección de entrega del pedido
    @ManyToOne
    @JoinColumn(name = "idDireccion")
    private DireccionCliente direccion;

    // Fecha en que se realizó el pedido (se establece automáticamente al crear el registro)
    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date fechaPedido;

    // Fecha en que se entregará el pedido (opcional)
    private Date fechaEntrega;

    // Estado del pedido (por defecto, "Pendiente")
    @Column(length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'Pendiente'")
    private String estado;

    // Total del pedido
    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "El total no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El total debe ser mayor a cero")
    private BigDecimal total;

    // Pago asociado al pedido
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPago")
    private Pago pago;

    // Detalles del pedido
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();

}
