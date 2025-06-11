package org.urban.springbootcafeteria.Entitie;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    //Identificador único del producto.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    //Nombre del producto.
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    //Descripción detallada del producto.
    @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    //Precio del producto.
    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0.01")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    //URL de la imagen del producto.
    @Size(max = 255, message = "La URL de la imagen no puede tener más de 255 caracteres")
    @Column(length = 255)
    private String imagen;

    //Categoría a la que pertenece el producto.
    @NotNull(message = "La categoría no puede ser nula")
    @ManyToOne
    @JoinColumn(name = "idCategoriaProducto")
    private CategoriaProducto categoriaProducto;


    //Constructor personalizado
    public Producto(String nombre, String descripcion, BigDecimal precio, String imagen, CategoriaProducto categoriaProducto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.categoriaProducto = categoriaProducto;
    }

}
