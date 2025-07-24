package com.test.inventario.inventario.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.test.inventario.inventario.dto.InventarioDTO;
import com.test.inventario.inventario.dto.ProductoDTO;
import com.test.inventario.inventario.feign.ProductoClient;
import com.test.inventario.inventario.model.Inventario;
import com.test.inventario.inventario.repository.HistorialCompraRepository;
import com.test.inventario.inventario.repository.InventarioRepository;
import com.test.inventario.inventario.util.Responses;

class InventarioServiceImplTest {

	@InjectMocks
    private InventarioServiceImpl inventarioService;

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoClient productoClient;

    @Mock
    private HistorialCompraRepository historialCompraRepository;

    private Inventario inventario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        inventario = new Inventario();
        inventario.setProductoId(1L);
        inventario.setCantidad(10);
    }

    @Test
    void testConsultarCantidad_Existente() {
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventario));

        Responses res = inventarioService.consultarCantidad(1L);

        assertFalse(res.isError());
        assertEquals("Inventario encontrado", res.getMessage());
    }
    
   
    @Test
    void consultarCantidad_deberiaRetornarErrorCuandoExcepcionOcurre() {
        Long productoId = 1L;
        when(inventarioRepository.findById(productoId)).thenThrow(new RuntimeException("Fallo en BD"));
        
        Responses respuesta = inventarioService.consultarCantidad(productoId);
        
        assertTrue(respuesta.isError());
        assertNull(respuesta.getResponse());
        assertTrue(respuesta.getMessage().contains("Error al consultar inventario"));
        assertTrue(respuesta.getMessage().contains("Fallo en BD"));
    }
    
    @Test
    void testConsultarCantidad_NoExistente() {
        when(inventarioRepository.findById(1L)).thenReturn(Optional.empty());

        Responses res = inventarioService.consultarCantidad(1L);

        assertTrue(res.isError());
        assertEquals("Inventario no encontrado", res.getMessage());
    }

    @Test
    void testActualizarCantidad_OK() {
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventario));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

        Responses res = inventarioService.actualizarCantidad(1L, 5);

        assertFalse(res.isError());
        assertEquals(5, ((Inventario) res.getResponse()).getCantidad());
    }
    
    
    @Test
    void testActualizarCantidad_resisError() {
        when(inventarioRepository.findById(1L)).thenReturn(Optional.empty());
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

        Responses res = inventarioService.actualizarCantidad(1L, 5);

        assertFalse(!res.isError());
    }
    
    
    @Test
    void testActualizarCantidad_lanzaExcepcionEnSave() {
        Inventario inventarioExcep = new Inventario();
        inventarioExcep.setProductoId(1L);
        inventarioExcep.setCantidad(10);
        
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventarioExcep));
        
        when(inventarioRepository.save(any(Inventario.class)))
                .thenThrow(new RuntimeException("Error simulado al guardar"));
        
        Responses response = inventarioService.actualizarCantidad(1L, 5);
        
        assertTrue(response.isError());
        assertNull(response.getResponse());
        assertEquals("Error al actualizar cantidad: Error simulado al guardar", response.getMessage());
    }
    

    @Test
    void testProcesarCompra_OK() {
        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Test Producto");
        producto.setPrecio(100.0);
        producto.setStock(10L);
        
        when(productoClient.obtenerProductoPorId(1L))
                .thenReturn(new Responses(producto, false, "Producto encontrado"));

        when(inventarioRepository.findById(1L))
                .thenReturn(Optional.of(inventario));

        when(inventarioRepository.save(any())).thenReturn(inventario);
        
        Responses res = inventarioService.procesarCompra(1L, 2);

        assertFalse(res.isError());
        assertTrue(res.getMessage().contains("Compra procesada correctamente"));
    }
    
    @Test
    void testProcesarCompra_resProductoisError() {
        when(productoClient.obtenerProductoPorId(1L))
                .thenReturn(new Responses(null, true, "Producto encontrado"));

        when(inventarioRepository.findById(1L))
                .thenReturn(Optional.of(inventario));

        when(inventarioRepository.save(any())).thenReturn(inventario);
        
        Responses res = inventarioService.procesarCompra(1L, 2);

        assertFalse(!res.isError());
        assertTrue(res.getMessage().contains("Producto no encontrado con ID"));
    }
    
    
    @Test
    void testProcesarCompra_resInventarioisError() {
        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Test Producto");
        producto.setPrecio(100.0);
        producto.setStock(10L);
        
        when(productoClient.obtenerProductoPorId(1L))
                .thenReturn(new Responses(producto, false, "Producto encontrado"));

        when(inventarioRepository.findById(1L))
                .thenReturn(Optional.empty());

        when(inventarioRepository.save(any())).thenReturn(inventario);
        
        Responses res = inventarioService.procesarCompra(1L, 2);

        assertFalse(!res.isError());
        assertTrue(res.getMessage().contains("Inventario no encontrado para el producto con ID"));
    }
    
    @Test
    void testProcesarCompra_lanzaExcepcion() {
        when(productoClient.obtenerProductoPorId(1L))
                .thenThrow(new RuntimeException("Fallo simulado"));
        
        Responses response = inventarioService.procesarCompra(1L, 2);

        
        assertTrue(response.isError());
        assertTrue(response.getMessage().contains("Error al procesar la compra"));
        assertTrue(response.getMessage().contains("Fallo simulado"));
    }

    
    @Test
    void testProcesarCompra_InventarioInsuficiente() {
        inventario.setCantidad(1);

        ProductoDTO producto = new ProductoDTO();
        producto.setId(1L);
        producto.setNombre("Test Producto");
        producto.setPrecio(100.0);
        producto.setStock(1L);

        when(productoClient.obtenerProductoPorId(1L))
                .thenReturn(new Responses(producto, false, "Producto encontrado"));

        when(inventarioRepository.findById(1L))
                .thenReturn(Optional.of(inventario));

        Responses res = inventarioService.procesarCompra(1L, 5);

        assertTrue(res.isError());
        assertEquals("Inventario insuficiente para el producto: Test Producto", res.getMessage());
    }
    
    
    @Test
    void testCrearInventario_lanzaExcepcion() {
        InventarioDTO dto = new InventarioDTO();
        dto.setProductoId(1L);
        dto.setCantidad(10);
        
        when(inventarioRepository.save(any(Inventario.class)))
                .thenThrow(new RuntimeException("Fallo en base de datos"));

        Responses res = inventarioService.crearInventario(dto);

        assertTrue(res.isError());
        assertNotNull(res.getMessage());
        assertTrue(res.getMessage().contains("Error al crear inventario"));
        assertTrue(res.getMessage().contains("Fallo en base de datos"));
    }
    

    @Test
    void testCrearInventario_OK() {
        InventarioDTO dto = new InventarioDTO(1L, 20);
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

        Responses res = inventarioService.crearInventario(dto);

        assertFalse(res.isError());
        assertEquals("Inventario creado correctamente", res.getMessage());
    }
}