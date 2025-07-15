package org.urban.springbootcafeteria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.urban.springbootcafeteria.dto.request.CategoriaProductoRequest;
import org.urban.springbootcafeteria.dto.response.CategoriaProductoResponse;
import org.urban.springbootcafeteria.entitie.CategoriaProducto;
import org.urban.springbootcafeteria.repository.CategoriaProductoRepository;
import org.urban.springbootcafeteria.service.CategoriaProductoService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaProductoServiceImpl implements CategoriaProductoService {
    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    @Override
    public Integer crear(CategoriaProductoRequest request) {
        CategoriaProducto categoria = new CategoriaProducto();
        categoria.setNombreCategoria(request.getNombreCategoria());
        categoriaProductoRepository.save(categoria);
        return categoria.getIdCategoriaProducto();
    }

    @Override
    public CategoriaProductoResponse obtenerPorId(Integer id) {
        CategoriaProducto categoria = categoriaProductoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return mapToResponse(categoria);
    }

    @Override
    public CategoriaProductoResponse actualizar(Integer id, CategoriaProductoRequest request) {
        CategoriaProducto categoria = categoriaProductoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        categoria.setNombreCategoria(request.getNombreCategoria());
        categoriaProductoRepository.save(categoria);
        return mapToResponse(categoria);
    }

    @Override
    public void eliminar(Integer id) {
        categoriaProductoRepository.deleteById(id);
    }

    @Override
    public List<CategoriaProductoResponse> listarTodos() {
        return categoriaProductoRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private CategoriaProductoResponse mapToResponse(CategoriaProducto categoria) {
        CategoriaProductoResponse response = new CategoriaProductoResponse();
        response.setIdCategoriaProducto(categoria.getIdCategoriaProducto());
        response.setNombreCategoria(categoria.getNombreCategoria());
        return response;
    }
}
