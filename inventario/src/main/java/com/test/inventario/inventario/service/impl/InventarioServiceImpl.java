package com.test.inventario.inventario.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.inventario.inventario.dto.InventarioDTO;
import com.test.inventario.inventario.dto.ProductoDTO;
import com.test.inventario.inventario.feign.ProductoClient;
import com.test.inventario.inventario.model.HistorialCompra;
import com.test.inventario.inventario.model.Inventario;
import com.test.inventario.inventario.repository.HistorialCompraRepository;
import com.test.inventario.inventario.repository.InventarioRepository;
import com.test.inventario.inventario.service.InventarioService;
import com.test.inventario.inventario.util.Responses;

@Service
public class InventarioServiceImpl implements InventarioService {
	
	@Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoClient productoClient;
    
    @Autowired
    private HistorialCompraRepository historialCompraRepository;

    @Override
    public Responses consultarCantidad(Long productoId) {
        try {
            Optional<Inventario> inventarioOpt = inventarioRepository.findById(productoId);
            if (inventarioOpt.isPresent()) {
                return new Responses(inventarioOpt.get(), false, "Inventario encontrado");
            } else {
                return new Responses(null, true, "Inventario no encontrado");
            }
        } catch (Exception e) {
            return new Responses(null, true, "Error al consultar inventario: " + e.getMessage());
        }
    }

    @Override
    public Responses actualizarCantidad(Long productoId, Integer cantidad) {
    	try {
            Responses res = consultarCantidad(productoId);
            
            if (res.isError() || res.getResponse() == null) {
                return new Responses(null, true, "No se encontró inventario para el producto con ID: " + productoId);
            }
            
            Inventario inventario = (Inventario) res.getResponse();
            inventario.setCantidad(cantidad);
            Inventario actualizado = inventarioRepository.save(inventario);

            return new Responses(actualizado, false, "Cantidad actualizada exitosamente");

        } catch (Exception e) {
            return new Responses(null, true, "Error al actualizar cantidad: " + e.getMessage());
        }
    }

    @Override
    public Responses procesarCompra(Long productoId, Integer cantidad) {
        try {
            Responses resProducto = productoClient.obtenerProductoPorId(productoId);
            if (resProducto.isError() || resProducto.getResponse() == null) {
                return new Responses(null, true, "Producto no encontrado con ID: " + productoId);
            }

            ObjectMapper mapper = new ObjectMapper();
            ProductoDTO producto = mapper.convertValue(resProducto.getResponse(), ProductoDTO.class);
            
            Responses resInventario = consultarCantidad(productoId);
            if (resInventario.isError() || resInventario.getResponse() == null) {
                return new Responses(null, true, "Inventario no encontrado para el producto con ID: " + productoId);
            }

            Inventario inventario = (Inventario) resInventario.getResponse();
            
            if (inventario.getCantidad() < cantidad) {
                return new Responses(null, true, "Inventario insuficiente para el producto: " + producto.getNombre());
            }
            
            inventario.setCantidad(inventario.getCantidad() - cantidad);
            inventarioRepository.save(inventario);
            
            producto.setStock(producto.getStock() - cantidad);
            productoClient.actualizarProducto(productoId, producto);
            
            this.registrarHistorial(productoId, cantidad);
            
            String mensajeCompra = String.format(
                "Compra realizada: Producto '%s' x%d - Total: $%.2f",
                producto.getNombre(), cantidad, producto.getPrecio() * cantidad
            );

            return new Responses(mensajeCompra, false, "Compra procesada correctamente");

        } catch (Exception e) {
            return new Responses(null, true, "Error al procesar la compra: " + e.getMessage());
        }
    }
    
    @Override
    public Responses crearInventario(InventarioDTO dto) {
    	try {
            Inventario inventario = new Inventario();
            inventario.setProductoId(dto.getProductoId());
            inventario.setCantidad(dto.getCantidad());

            Inventario inventarioGuardado = inventarioRepository.save(inventario);

            return new Responses(inventarioGuardado, false, "Inventario creado correctamente");
        } catch (Exception e) {
            return new Responses(null, true, "Error al crear inventario: " + e.getMessage());
        }
    }
    

    public void registrarHistorial(Long productoId, Integer cantidad) {
        HistorialCompra historial = HistorialCompra.builder()
                .productoId(productoId)
                .cantidad(cantidad)
                .fechaCompra(LocalDateTime.now())
                .build();

        historialCompraRepository.save(historial);
    }
}