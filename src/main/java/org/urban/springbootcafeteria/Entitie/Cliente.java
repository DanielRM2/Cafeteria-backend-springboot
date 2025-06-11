package org.urban.springbootcafeteria.Entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

@Entity
@Table(name = "Cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    // Identificador único del cliente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    // Nombre completo del cliente
    @NotBlank(message = "El nombre completo no puede estar en blanco")
    @Size(max = 100, message = "El nombre completo no puede tener más de 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombreCompleto;

    // Dirección de correo electrónico del cliente (debe ser única)
    @NotBlank(message = "El correo electrónico no puede estar en blanco")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 100, message = "El correo electrónico no puede tener más de 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // Contraseña del cliente
    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 5, max = 255, message = "La contraseña debe tener entre 5 y 255 caracteres")
    @Column(nullable = false, length = 255)
    private String contrasena;

    // Número de teléfono del cliente (solo Perú)
    @NotBlank(message = "El teléfono no puede estar en blanco")
    @Pattern(regexp = "^9\\d{8}$", message = "El teléfono debe ser un número de celular peruano válido (9 dígitos, comenzando con 9)")
    @Column(nullable = false, length = 15)
    private String telefono;

    // Fecha de nacimiento del cliente
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

}
