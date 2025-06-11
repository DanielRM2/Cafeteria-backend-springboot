package org.urban.springbootcafeteria.Service;

import org.urban.springbootcafeteria.Dto.ProductoResponse;
import java.util.List;

public interface ProductoService {

    List<ProductoResponse> listarProductosOrdenadosPorCategoria();

    List<ProductoResponse> listarPrimerosProductos();


}
