package org.urban.springbootcafeteria.entitie;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "Rol")
@Data

public class Rol implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 20)
    private String nombre;

    @Override
    public String getAuthority() {
        return "ROLE_" + nombre; // Spring Security espera el prefijo ROLE_
    }
}
