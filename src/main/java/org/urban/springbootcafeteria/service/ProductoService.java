package org.urban.springbootcafeteria.service;

import org.urban.springbootcafeteria.dto.response.ProductoResponse;
import org.urban.springbootcafeteria.dto.request.ProductoRequest;
import java.util.List;

public interface ProductoService {

    // CRUD y listar
    List<ProductoResponse> listarProductosOrdenadosPorCategoria();
    List<ProductoResponse> listarPrimerosProductos();
    ProductoResponse crear(ProductoRequest request);
    ProductoResponse obtenerPorId(Integer id);
    ProductoResponse actualizar(Integer id, ProductoRequest request);
    void eliminar(Integer id);
    List<ProductoResponse> listarTodos();

}
