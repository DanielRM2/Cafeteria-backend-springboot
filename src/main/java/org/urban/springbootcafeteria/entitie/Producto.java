package org.urban.springbootcafeteria.entitie;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "Producto")
@Data
public class Producto {

    //Identificador único del producto.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    //Nombre del producto.
    @Column(nullable = false, length = 100)
    private String nombre;

    //Descripción detallada del producto.
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    //Precio del producto.
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    //URL de la imagen del producto.
    private String imagen;

    //Categoría a la que pertenece el producto.
    @ManyToOne
    @JoinColumn(name = "idCategoriaProducto")
    private CategoriaProducto categoriaProducto;

    // Inventario del producto
    @OneToOne(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventario inventario;

    //Constructor vacío
    public Producto() {
    }

    //Constructor personalizado
    public Producto(String nombre, String descripcion, BigDecimal precio, String imagen, CategoriaProducto categoriaProducto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.categoriaProducto = categoriaProducto;
    }

}
