package org.urban.springbootcafeteria.Entitie;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Empleado")
@Data
public class Empleado {

    // Identificador único del empleado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpleado;

    // Nombre del empleado
    @Column(nullable = false, length = 100)
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    // Teléfono del empleado
    @NotBlank(message = "El teléfono no puede estar en blanco")
    @Pattern(regexp = "^9\\d{8}$", message = "El teléfono debe ser un número de celular peruano válido (9 dígitos, comenzando con 9)")
    @Column(nullable = false, length = 15)
    private String telefono;

    // Correo electrónico del empleado (debe ser único)
    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El correo electrónico no puede estar en blanco")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 100, message = "El correo electrónico no puede tener más de 100 caracteres")
    private String email;

    // Contraseña del empleado
    @Column(nullable = false, length = 255)
    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 8, max = 255, message = "La contraseña debe tener entre 8 y 255 caracteres")
    private String contrasena;

}
