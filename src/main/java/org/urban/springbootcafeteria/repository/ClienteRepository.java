package org.urban.springbootcafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.urban.springbootcafeteria.entitie.Cliente;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}