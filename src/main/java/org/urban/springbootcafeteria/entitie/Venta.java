package org.urban.springbootcafeteria.entitie;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVenta;

    // Relaciona la venta con el pedido confirmado
    @OneToOne
    @JoinColumn(name = "idPedido", nullable = false)
    private Pedido pedido;

    // Monto total de la venta (snapshot en el momento de la confirmación)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // Fecha y hora de la confirmación de la venta
    @Column(nullable = false)
    private LocalDateTime fechaVenta;

    // Estado de la venta (por ejemplo: FINALIZADA, ANULADA)
    @Enumerated(EnumType.STRING)
    private EstadoVenta estado;

    public enum EstadoVenta {
        FINALIZADA, ANULADA
    }
}
