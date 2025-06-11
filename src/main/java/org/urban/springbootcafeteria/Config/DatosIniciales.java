package org.urban.springbootcafeteria.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.urban.springbootcafeteria.Entitie.CategoriaProducto;
import org.urban.springbootcafeteria.Entitie.Inventario;
import org.urban.springbootcafeteria.Entitie.MetodoEntrega;
import org.urban.springbootcafeteria.Entitie.Producto;
import org.urban.springbootcafeteria.Repository.CategProductoRepository;
import org.urban.springbootcafeteria.Repository.InventarioRepository;
import org.urban.springbootcafeteria.Repository.MetodoEntregaRepository;
import org.urban.springbootcafeteria.Repository.ProductoRepository;
import java.math.BigDecimal;
import java.util.List;

@Component
public class DatosIniciales implements CommandLineRunner {

    @Autowired
    private CategProductoRepository categoriaRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private MetodoEntregaRepository metodoEntregaRepo;

    @Autowired
    private InventarioRepository inventarioRepo;


    @Override
    public void run(String... args) {
        // Insertar categorías
        if (categoriaRepo.count() == 0) {
            CategoriaProducto cafe = new CategoriaProducto();
            cafe.setNombreCategoria("Cafes");
            categoriaRepo.save(cafe);

            CategoriaProducto postre = new CategoriaProducto();
            postre.setNombreCategoria("Postres");
            categoriaRepo.save(postre);

            CategoriaProducto pan = new CategoriaProducto();
            pan.setNombreCategoria("Panes");
            categoriaRepo.save(pan);

            // Insertar productos
            if (productoRepo.count() == 0) {
                productoRepo.save(new Producto("Café clásico", "Espresso puro y caliente", new BigDecimal("9.90"), "cafe_clasico.jpg", cafe));
                productoRepo.save(new Producto("Capricho de chocolate", "Café con chocolate, crema batida y chispas dulces.", new BigDecimal("12.50"), "capricho_chocolate.jpg", cafe));
                productoRepo.save(new Producto("Moca cremoso", "Café con leche, chocolate batido y crema derretida", new BigDecimal("13.50"), "moca_cremoso.jpg", cafe));
                productoRepo.save(new Producto("Café canela", "Cafe con leche, aromatizado con canela", new BigDecimal("11.90"), "cafe_canela.jpg", cafe));
                productoRepo.save(new Producto("Latte explosivo", "Latte suave pero energético", new BigDecimal("10.90"), "latte_explosivo.jpg", cafe));

                productoRepo.save(new Producto("Caramelo supremo", "Postre de caramelo con base de galleta", new BigDecimal("10.90"), "caramelo_supremo.jpg", postre));
                productoRepo.save(new Producto("Bombón de chocolate", "Bombones rellenos de crema de chocolate", new BigDecimal("9.90"), "bombon_chocolate.jpg", postre));
                productoRepo.save(new Producto("Tiramisú clásico", "Postre italiano con café y cacao", new BigDecimal("8.90"), "tiramisu.jpg", postre));
                productoRepo.save(new Producto("Tentación de frutos rojos", "Postre con frutos rojos y crema", new BigDecimal("10.50"), "frutos_rojos.jpg", postre));
                productoRepo.save(new Producto("Copa primavera", "Postre ligero con frutas de temporada", new BigDecimal("8.50"), "copa_primavera.jpg", postre));
                productoRepo.save(new Producto("Tarta de limón", "Tarta cremosa de limón con merengue", new BigDecimal("8.90"), "tarta_limon.jpg", postre));

                productoRepo.save(new Producto("Croissant clásico", "Croissant de mantequilla tradicional", new BigDecimal("9.90"), "croissant.jpg", pan));
                productoRepo.save(new Producto("Sandwich de Jamón y vegetales", "Pan integral con jamón y vegetales frescos", new BigDecimal("8.90"), "sandwich_jamon.jpg", pan));
                productoRepo.save(new Producto("Rollo de canela", "Pan dulce con canela y azúcar", new BigDecimal("8.50"), "rollo_canela.jpg", pan));
                productoRepo.save(new Producto("Danish de Pasas", "Pan danés con pasas y glaseado", new BigDecimal("8.90"), "danish_pasas.jpg", pan));

                System.out.println("Productos y categorías iniciales insertados.");
            }

        }

        //Insertar inventario
        if (inventarioRepo.count() == 0) {
            List<Producto> productos = productoRepo.findAll();
            for (Producto producto : productos) {
                Inventario inventario = new Inventario();
                inventario.setProducto(producto);
                inventario.setCantidadDisponible(50);
                inventarioRepo.save(inventario);
            }

            System.out.println("Inventario inicial insertado para todos los productos.");
        }

        // Insertar métodos de entrega
        if (metodoEntregaRepo.count() == 0) {
            metodoEntregaRepo.save(new MetodoEntrega("Recojo en tienda", BigDecimal.ZERO));
            metodoEntregaRepo.save(new MetodoEntrega("Envío a domicilio", new BigDecimal("5.00")));

            System.out.println("Métodos de entrega iniciales insertados.");
        }

    }
}
