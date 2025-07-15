package org.urban.springbootcafeteria.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.urban.springbootcafeteria.dto.request.ProductoRequest;
import org.urban.springbootcafeteria.dto.response.ProductoResponse;
import org.urban.springbootcafeteria.entitie.CategoriaProducto;
import org.urban.springbootcafeteria.entitie.Inventario;
import org.urban.springbootcafeteria.entitie.Producto;
import org.urban.springbootcafeteria.repository.CategoriaProductoRepository;
import org.urban.springbootcafeteria.repository.InventarioRepository;
import org.urban.springbootcafeteria.repository.ProductoRepository;
import org.urban.springbootcafeteria.service.ProductoService;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public List<ProductoResponse> listarProductosOrdenadosPorCategoria() {
        return productoRepository.findAll().stream()
                .sorted((p1, p2) -> Integer.compare(
                        p1.getCategoriaProducto().getIdCategoriaProducto(),
                        p2.getCategoriaProducto().getIdCategoriaProducto()
                ))
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductoResponse> listarPrimerosProductos() {
        return productoRepository.findTop4ByOrderByIdProductoAsc().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductoResponse crear(ProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setImagen(request.getImagen());
        if (request.getIdCategoriaProducto() != null) {
            CategoriaProducto categoria = categoriaProductoRepository.findById(request.getIdCategoriaProducto())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoriaProducto(categoria);
        }
        productoRepository.save(producto);

        // Crear inventario con stock
        Inventario inventario = new Inventario();
        inventario.setProducto(producto);
        inventario.setCantidadDisponible(request.getStock() != null ? request.getStock() : 0);
        inventarioRepository.save(inventario);
        producto.setInventario(inventario);
        productoRepository.save(producto);

        return mapToResponse(producto);
    }

    @Override
    public ProductoResponse obtenerPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return mapToResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(Integer id, ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setImagen(request.getImagen());
        if (request.getIdCategoriaProducto() != null) {
            CategoriaProducto categoria = categoriaProductoRepository.findById(request.getIdCategoriaProducto())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoriaProducto(categoria);
        }
        productoRepository.save(producto);

        // Actualizar o crear inventario
        Inventario inventario = producto.getInventario();
        if (inventario == null) {
            inventario = new Inventario();
            inventario.setProducto(producto);
        }
        inventario.setCantidadDisponible(request.getStock() != null ? request.getStock() : 0);
        inventarioRepository.save(inventario);
        producto.setInventario(inventario);
        productoRepository.save(producto);

        return mapToResponse(producto);
    }

    @Override
    public void eliminar(Integer id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoRepository.delete(producto);
    }

    @Override
    public List<ProductoResponse> listarTodos() {
        return productoRepository.findAll().stream()
            .map(this::mapToResponse)
            .toList();
    }

    private ProductoResponse mapToResponse(Producto p) {
        ProductoResponse response = new ProductoResponse();
        response.setIdProducto(p.getIdProducto());
        response.setNombre(p.getNombre());
        response.setDescripcion(p.getDescripcion());
        response.setPrecio(p.getPrecio());
        response.setImagen(p.getImagen());
        if (p.getCategoriaProducto() != null) {
            response.setIdCategoriaProducto(p.getCategoriaProducto().getIdCategoriaProducto());
            response.setNombreCategoria(p.getCategoriaProducto().getNombreCategoria());
        }
        // Incluir stock
        if (p.getInventario() != null) {
            response.setStock(p.getInventario().getCantidadDisponible());
        } else {
            response.setStock(0);
        }
        return response;
    }
}
