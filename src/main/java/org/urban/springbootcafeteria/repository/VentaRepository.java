package org.urban.springbootcafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urban.springbootcafeteria.entitie.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    // Puedes agregar métodos personalizados aquí si lo necesitas
}
