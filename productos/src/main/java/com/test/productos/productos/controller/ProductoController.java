package com.test.productos.productos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.productos.productos.model.Producto;
import com.test.productos.productos.service.ProductoService;
import com.test.productos.productos.util.Responses;

@RestController
@RequestMapping("/productos")
public class ProductoController {

	private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<Responses> listarProductos() {
        Responses response = productoService.listarProductos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Responses> obtenerProductoPorId(@PathVariable Long id) {
        Responses response = productoService.obtenerProductoPorId(id);
        return response.isError()
                ? ResponseEntity.status(404).body(response)
                : ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Responses> crearProducto(@RequestBody Producto producto) {
        Responses response = productoService.crearProducto(producto);
        return response.isError()
                ? ResponseEntity.badRequest().body(response)
                : ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Responses> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Responses response = productoService.actualizarProducto(id, producto);
        return response.isError()
                ? ResponseEntity.status(404).body(response)
                : ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Responses> eliminarProducto(@PathVariable Long id) {
        Responses response = productoService.eliminarProducto(id);
        return response.isError()
                ? ResponseEntity.status(404).body(response)
                : ResponseEntity.ok(response);
    }
}