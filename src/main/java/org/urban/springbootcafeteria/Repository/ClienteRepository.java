package org.urban.springbootcafeteria.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.urban.springbootcafeteria.Entitie.Cliente;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // Metodo para buscar por email
    Optional<Cliente> findByEmail(String email);

    // Metodo para verificar si existe un email
    boolean existsByEmail(String email);

}