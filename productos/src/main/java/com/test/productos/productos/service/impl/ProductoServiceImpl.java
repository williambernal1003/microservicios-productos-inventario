package com.test.productos.productos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.test.productos.productos.dto.InventarioDTO;
import com.test.productos.productos.feign.InventarioClient;
import com.test.productos.productos.model.Producto;
import com.test.productos.productos.repository.ProductoRepository;
import com.test.productos.productos.service.ProductoService;
import com.test.productos.productos.util.Responses;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	private final ProductoRepository productoRepository;
    private final InventarioClient inventarioClient;

    public ProductoServiceImpl(ProductoRepository productoRepository, InventarioClient inventarioClient) {
        this.productoRepository = productoRepository;
        this.inventarioClient = inventarioClient;
    }

    @Override
    public Responses listarProductos() {
        try {
            List<Producto> productos = productoRepository.findAll();
            return new Responses(productos, false, "Productos listados correctamente");
        } catch (Exception e) {
            return new Responses(null, true, "Error al listar productos: " + e.getMessage());
        }
    }

    @Override
    public Responses obtenerProductoPorId(Long id) {
        try {
            Optional<Producto> productoOpt = productoRepository.findById(id);
            if (productoOpt.isPresent()) {
                return new Responses(productoOpt.get(), false, "Producto encontrado");
            } else {
                return new Responses(null, true, "Producto no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return new Responses(null, true, "Error al obtener producto: " + e.getMessage());
        }
    }

    @Override
    public Responses crearProducto(Producto producto) {
        try {
            Producto nuevoProducto = productoRepository.save(producto);

            InventarioDTO inventarioDTO = new InventarioDTO();
            inventarioDTO.setProductoId(nuevoProducto.getId());
            inventarioDTO.setCantidad(nuevoProducto.getStock());

            inventarioClient.crearInventario(inventarioDTO);

            return new Responses(nuevoProducto, false, "Producto creado exitosamente");
        } catch (Exception e) {
            return new Responses(null, true, "Error al crear producto: " + e.getMessage());
        }
    }

    @Override
    public Responses actualizarProducto(Long id, Producto producto) {
        try {
            return productoRepository.findById(id)
                .map(p -> {
                    p.setNombre(producto.getNombre());
                    p.setDescripcion(producto.getDescripcion());
                    p.setPrecio(producto.getPrecio());
                    p.setStock(producto.getStock());
                    Producto actualizado = productoRepository.save(p);
                    return new Responses(actualizado, false, "Producto actualizado correctamente");
                })
                .orElseGet(() -> new Responses(null, true, "Producto no encontrado con ID: " + id));
        } catch (Exception e) {
            return new Responses(null, true, "Error al actualizar producto: " + e.getMessage());
        }
    }

    @Override
    public Responses eliminarProducto(Long id) {
        try {
            productoRepository.deleteById(id);
            return new Responses(null, false, "Producto eliminado correctamente");
        } catch (Exception e) {
            return new Responses(null, true, "Error al eliminar producto: " + e.getMessage());
        }
    }
}