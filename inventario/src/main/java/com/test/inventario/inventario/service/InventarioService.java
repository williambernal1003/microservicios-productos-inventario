package com.test.inventario.inventario.service;

import com.test.inventario.inventario.dto.InventarioDTO;
import com.test.inventario.inventario.util.Responses;

public interface InventarioService {
	Responses consultarCantidad(Long productoId);
	Responses actualizarCantidad(Long productoId, Integer cantidad);
	Responses procesarCompra(Long productoId, Integer cantidad);
	Responses crearInventario(InventarioDTO dto);
}