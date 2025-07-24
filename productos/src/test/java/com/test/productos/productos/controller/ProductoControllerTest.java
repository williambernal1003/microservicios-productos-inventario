package com.test.productos.productos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.test.productos.productos.model.Producto;
import com.test.productos.productos.service.ProductoService;
import com.test.productos.productos.util.Responses;

class ProductoControllerTest {

	@Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarProductos() {
        Responses mockResponse = new Responses();
        mockResponse.setError(false);
        mockResponse.setMessage("Listado exitoso");
        mockResponse.setResponse(null);
        when(productoService.listarProductos()).thenReturn(mockResponse);

        ResponseEntity<Responses> response = productoController.listarProductos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Listado exitoso", response.getBody().getMessage());
    }

    @Test
    void testObtenerProductoPorId_Exito() {
        Responses mockResponse = new Responses();
        mockResponse.setError(false);
        mockResponse.setMessage("Producto encontrado");
        mockResponse.setResponse(null);
        when(productoService.obtenerProductoPorId(1L)).thenReturn(mockResponse);

        ResponseEntity<Responses> response = productoController.obtenerProductoPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Producto encontrado", response.getBody().getMessage());
    }

    @Test
    void testObtenerProductoPorId_NoEncontrado() {
        Responses mockResponse = new Responses();
        mockResponse.setError(true);
        mockResponse.setMessage("Producto no encontrado");
        mockResponse.setResponse(null);
        when(productoService.obtenerProductoPorId(1L)).thenReturn(mockResponse);

        ResponseEntity<Responses> response = productoController.obtenerProductoPorId(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Producto no encontrado", response.getBody().getMessage());
    }

    @Test
    void testCrearProducto_Exito() {
        Producto producto = new Producto();
        Responses mockResponse = new Responses();
        mockResponse.setError(false);
        mockResponse.setMessage("Creado exitosamente");
        mockResponse.setResponse(null);
        when(productoService.crearProducto(producto)).thenReturn(mockResponse);

        ResponseEntity<Responses> response = productoController.crearProducto(producto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Creado exitosamente", response.getBody().getMessage());
    }

    @Test
    void testCrearProducto_Error() {
        Producto producto = new Producto();
        Responses mockResponse = new Responses();
        mockResponse.setError(true);
        mockResponse.setMessage("Error al crear");
        mockResponse.setResponse(null);
        when(productoService.crearProducto(producto)).thenReturn(mockResponse);

        ResponseEntity<Responses> response = productoController.crearProducto(producto);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error al crear", response.getBody().getMessage());
    }

    @Test
    void testActualizarProducto_Exito() {
        Producto producto = new Producto();
        Responses mockResponse = new Responses();
        mockResponse.setError(false);
        mockResponse.setMessage("Actualizado correctamente");
        mockResponse.setResponse(null);
        when(productoService.actualizarProducto(1L, producto)).thenReturn(mockResponse);

        ResponseEntity<Responses> response = productoController.actualizarProducto(1L, producto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Actualizado correctamente", response.getBody().getMessage());
    }

    @Test
    void testActualizarProducto_NoEncontrado() {
        Producto producto = new Producto();
        Responses mockResponse = new Responses();
        mockResponse.setError(true);
        mockResponse.setMessage("Producto no encontrado");
        mockResponse.setResponse(null);
        when(productoService.actualizarProducto(1L, producto)).thenReturn(mockResponse);

        ResponseEntity<Responses> response = productoController.actualizarProducto(1L, producto);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Producto no encontrado", response.getBody().getMessage());
    }

    @Test
    void testEliminarProducto_Exito() {
        Responses mockResponse = new Responses();
        mockResponse.setError(false);
        mockResponse.setMessage("Eliminado correctamente");
        mockResponse.setResponse(null);
        when(productoService.eliminarProducto(1L)).thenReturn(mockResponse);

        ResponseEntity<Responses> response = productoController.eliminarProducto(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Eliminado correctamente", response.getBody().getMessage());
    }

    @Test
    void testEliminarProducto_NoEncontrado() {
        Responses mockResponse = new Responses();
        mockResponse.setError(true);
        mockResponse.setMessage("No encontrado");
        mockResponse.setResponse(null);
        when(productoService.eliminarProducto(1L)).thenReturn(mockResponse);

        ResponseEntity<Responses> response = productoController.eliminarProducto(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("No encontrado", response.getBody().getMessage());
    }
}