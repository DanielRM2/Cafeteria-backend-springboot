package org.urban.springbootcafeteria.Entitie;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Transaccion")
@Data
public class Transaccion {

    // Identificador único de la transacción
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTransaccion;

    // Pedido asociado a la transacción
    @OneToOne
    @JoinColumn(name = "idPedido", nullable = false)
    @NotNull(message = "Debe seleccionar un pedido")
    private Pedido pedido;

    // Tipo de pago utilizado en la transacción
    @ManyToOne
    @JoinColumn(name = "idTipoPago", nullable = false)
    @NotNull(message = "Debe seleccionar un tipo de pago")
    private TipoPago tipoPago;

    // Estado de la transacción (por defecto, "Pendiente")
    @Column(length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'Pendiente'")
    @Size(max = 50, message = "El estado no puede tener más de 50 caracteres")
    private String estado;

    // Fecha en que se realizó la transacción (se establece automáticamente al crear el registro)
    @Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date fechaTransaccion;

    // Código de operación de la transacción (opcional)
    @Column(length = 50)
    @Size(max = 50, message = "El código de operación no puede tener más de 50 caracteres")
    private String codigoOperacion;

    // URL del comprobante de la transacción
    @Column(length = 500) // Define la longitud máxima de la columna
    @Size(max = 500, message = "La URL del comprobante no puede tener más de 500 caracteres")
    private String comprobanteUrl;

    // Monto de la transacción
    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private BigDecimal monto;

}
