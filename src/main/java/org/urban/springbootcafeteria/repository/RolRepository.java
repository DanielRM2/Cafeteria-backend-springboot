package org.urban.springbootcafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urban.springbootcafeteria.entitie.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}