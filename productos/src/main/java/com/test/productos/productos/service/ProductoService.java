package com.test.productos.productos.service;

import com.test.productos.productos.model.Producto;
import com.test.productos.productos.util.Responses;

public interface ProductoService {
	Responses listarProductos();
	Responses obtenerProductoPorId(Long id);
	Responses crearProducto(Producto producto);
	Responses actualizarProducto(Long id, Producto producto);
	Responses eliminarProducto(Long id);
}