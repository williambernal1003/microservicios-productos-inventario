package com.test.inventario.inventario.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.test.inventario.inventario.dto.ProductoDTO;
import com.test.inventario.inventario.util.Responses;

@FeignClient(name = "producto-service", url = "${servicio.productos.url}")
public interface ProductoClient {
	
	@GetMapping("/productos/{id}")
	Responses obtenerProductoPorId(@PathVariable("id") Long id);
	
	@PutMapping("/productos/{id}")
	Responses actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO producto);
}