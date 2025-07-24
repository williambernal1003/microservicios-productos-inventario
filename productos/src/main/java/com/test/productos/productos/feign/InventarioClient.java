package com.test.productos.productos.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.test.productos.productos.dto.InventarioDTO;

@FeignClient(name = "inventario-service", url = "${servicio.inventario.url}")
public interface InventarioClient {

    @PostMapping("/inventario")
    void crearInventario(@RequestBody InventarioDTO inventarioDTO);
}