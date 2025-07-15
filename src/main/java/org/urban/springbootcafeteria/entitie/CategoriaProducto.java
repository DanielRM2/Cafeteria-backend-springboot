package org.urban.springbootcafeteria.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CategoriaProducto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaProducto {

    //Identificador único de la categoria de producto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoriaProducto;

    //Nombre de la categoria.
    @NotBlank(message = "El nombre de la categoría no puede estar en blanco")
    @Size(max = 100, message = "El nombre de la categoría no puede tener más de 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombreCategoria;

}
