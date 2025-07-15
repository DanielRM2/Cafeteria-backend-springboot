package org.urban.springbootcafeteria.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;

    @OneToOne(mappedBy = "pago")
    private Pedido pedido;

    private BigDecimal monto;

    private LocalDateTime fechaPago;

    public enum EstadoPago {
        PENDIENTE, COMPLETADO, RECHAZADO, EN_PROCESO
    }

    @Enumerated(EnumType.STRING)
    private EstadoPago estado;

    // --- Campos específicos de Mercado Pago ---
    private String mercadoPagoId;             // ID del pago real (payment.getId())
    private String mercadoPagoPreferenceId;   // ID de la preferencia (preference.getId())
    private String mercadoPagoStatus;         // Estado detallado reportado por Mercado Pago
    private String paymentLink;               // Link para que el usuario realice el pago
    private String payerEmail;                // Correo del pagador (opcional)
}
