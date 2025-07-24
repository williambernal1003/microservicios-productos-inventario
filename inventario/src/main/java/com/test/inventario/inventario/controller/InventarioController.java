package com.test.inventario.inventario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.inventario.inventario.dto.CompraRequest;
import com.test.inventario.inventario.dto.InventarioDTO;
import com.test.inventario.inventario.service.InventarioService;
import com.test.inventario.inventario.util.Responses;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

	@Autowired
    private InventarioService inventarioService;

    @GetMapping("/{id}")
    public Responses consultarInventario(@PathVariable Long id) {
        return inventarioService.consultarCantidad(id);
    }

    @PutMapping("/{id}")
    public Responses actualizarInventario(@PathVariable Long id, @RequestParam Integer cantidad) {
        return inventarioService.actualizarCantidad(id, cantidad);
    }

    @PostMapping("/comprar")
    public Responses comprarProducto(@RequestBody CompraRequest request) {
        return inventarioService.procesarCompra(request.getProductoId(), request.getCantidad());
    }
    
    @PostMapping
    public Responses crearInventario(@RequestBody InventarioDTO inventarioDTO) {
        return inventarioService.crearInventario(inventarioDTO);
    }
}