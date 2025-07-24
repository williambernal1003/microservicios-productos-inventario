package com.test.productos.productos.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.test.productos.productos.dto.InventarioDTO;
import com.test.productos.productos.feign.InventarioClient;
import com.test.productos.productos.model.Producto;
import com.test.productos.productos.repository.ProductoRepository;
import com.test.productos.productos.util.Responses;

class ProductoServiceImplTest {

	private ProductoRepository productoRepository;
    private InventarioClient inventarioClient;
    private ProductoServiceImpl productoService;

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepository.class);
        inventarioClient = mock(InventarioClient.class);
        productoService = new ProductoServiceImpl(productoRepository, inventarioClient);
    }

    @Test
    void listarProductos_Success() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(new Producto()));
        Responses response = productoService.listarProductos();
        assertFalse(response.isError());
        assertEquals("Productos listados correctamente", response.getMessage());
    }

    @Test
    void listarProductos_Exception() {
        when(productoRepository.findAll()).thenThrow(new RuntimeException("DB Error"));
        Responses response = productoService.listarProductos();
        assertTrue(response.isError());
        assertTrue(response.getMessage().contains("Error al listar productos"));
    }

    @Test
    void obtenerProductoPorId_Success() {
        Producto producto = new Producto();
        producto.setId(1L);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        Responses response = productoService.obtenerProductoPorId(1L);
        assertFalse(response.isError());
        assertEquals("Producto encontrado", response.getMessage());
    }

    @Test
    void obtenerProductoPorId_NotFound() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());
        Responses response = productoService.obtenerProductoPorId(1L);
        assertTrue(response.isError());
        assertTrue(response.getMessage().contains("Producto no encontrado"));
    }

    @Test
    void obtenerProductoPorId_Exception() {
        when(productoRepository.findById(anyLong())).thenThrow(new RuntimeException("Error inesperado"));
        Responses response = productoService.obtenerProductoPorId(1L);
        assertTrue(response.isError());
        assertTrue(response.getMessage().contains("Error al obtener producto"));
    }

    @Test
    void crearProducto_Success() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setStock(10);

        when(productoRepository.save(any())).thenReturn(producto);

        Responses response = productoService.crearProducto(producto);
        assertFalse(response.isError());
        assertEquals("Producto creado exitosamente", response.getMessage());
        verify(inventarioClient, times(1)).crearInventario(any(InventarioDTO.class));
    }

    @Test
    void crearProducto_Exception() {
        Producto producto = new Producto();
        when(productoRepository.save(any())).thenThrow(new RuntimeException("Error al guardar"));
        Responses response = productoService.crearProducto(producto);
        assertTrue(response.isError());
        assertTrue(response.getMessage().contains("Error al crear producto"));
    }

    @Test
    void actualizarProducto_Success() {
        Producto producto = new Producto();
        producto.setNombre("Nuevo nombre");

        Producto existente = new Producto();
        existente.setId(1L);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(productoRepository.save(any())).thenReturn(existente);

        Responses response = productoService.actualizarProducto(1L, producto);
        assertFalse(response.isError());
        assertEquals("Producto actualizado correctamente", response.getMessage());
    }

    @Test
    void actualizarProducto_NotFound() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());
        Responses response = productoService.actualizarProducto(1L, new Producto());
        assertTrue(response.isError());
        assertTrue(response.getMessage().contains("Producto no encontrado"));
    }

    @Test
    void actualizarProducto_Exception() {
        when(productoRepository.findById(anyLong())).thenThrow(new RuntimeException("Error inesperado"));
        Responses response = productoService.actualizarProducto(1L, new Producto());
        assertTrue(response.isError());
        assertTrue(response.getMessage().contains("Error al actualizar producto"));
    }

    @Test
    void eliminarProducto_Success() {
        doNothing().when(productoRepository).deleteById(1L);
        Responses response = productoService.eliminarProducto(1L);
        assertFalse(response.isError());
        assertEquals("Producto eliminado correctamente", response.getMessage());
    }

    @Test
    void eliminarProducto_Exception() {
        doThrow(new RuntimeException("Fallo al eliminar")).when(productoRepository).deleteById(1L);
        Responses response = productoService.eliminarProducto(1L);
        assertTrue(response.isError());
        assertTrue(response.getMessage().contains("Error al eliminar producto"));
    }
}