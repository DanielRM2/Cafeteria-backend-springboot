package org.urban.springbootcafeteria.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.Repository.ProductoRepository;
import org.urban.springbootcafeteria.Dto.ProductoResponse;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<ProductoResponse> listarProductosOrdenadosPorCategoria() {
        return productoRepository.findAll().stream()
                .sorted((p1, p2) -> Integer.compare(
                        p1.getCategoriaProducto().getIdCategoriaProducto(),
                        p2.getCategoriaProducto().getIdCategoriaProducto()
                ))
                .map(p -> {
                    ProductoResponse response = new ProductoResponse();
                    response.setIdProducto(p.getIdProducto());
                    response.setNombre(p.getNombre());
                    response.setDescripcion(p.getDescripcion());
                    response.setPrecio(p.getPrecio());
                    response.setImagen(p.getImagen());
                    response.setIdCategoriaProducto(p.getCategoriaProducto().getIdCategoriaProducto());
                    response.setNombreCategoria(p.getCategoriaProducto().getNombreCategoria());
                    return response;
                })
                .toList();
    }

    @Override
    public List<ProductoResponse> listarPrimerosProductos() {
        return productoRepository.findAll().stream()
                .limit(4)
                .map(p -> {
                    ProductoResponse response = new ProductoResponse();
                    response.setIdProducto(p.getIdProducto());
                    response.setNombre(p.getNombre());
                    response.setDescripcion(p.getDescripcion());
                    response.setPrecio(p.getPrecio());
                    response.setImagen(p.getImagen());
                    response.setIdCategoriaProducto(p.getCategoriaProducto().getIdCategoriaProducto());
                    response.setNombreCategoria(p.getCategoriaProducto().getNombreCategoria());
                    return response;
                })
                .toList();
    }

}
 