package org.urban.springbootcafeteria.entitie;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Staff")
@Data
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombreCompleto;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @Column(nullable = false)
    private boolean activo = true;
}
