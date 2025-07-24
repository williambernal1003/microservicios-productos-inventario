package com.test.inventario.inventario.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.inventario.inventario.dto.CompraRequest;
import com.test.inventario.inventario.dto.InventarioDTO;
import com.test.inventario.inventario.service.InventarioService;
import com.test.inventario.inventario.util.Responses;

public class InventarioControllerTest {
	private MockMvc mockMvc;

    @Mock
    private InventarioService inventarioService;

    @InjectMocks
    private InventarioController inventarioController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(inventarioController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void consultarInventarioTest() throws Exception {
        Long productoId = 1L;
        Responses response = new Responses();
        response.setError(true);
        response.setMessage("Error");
        response.setResponse(null);

        when(inventarioService.consultarCantidad(productoId)).thenReturn(response);

        mockMvc.perform(get("/inventario/{id}", productoId))
                .andExpect(status().isOk());

        verify(inventarioService, times(1)).consultarCantidad(productoId);
    }

    @Test
    void actualizarInventarioTest() throws Exception {
        Long productoId = 1L;
        Integer cantidad = 10;
        Responses response = new Responses();
        response.setError(true);
        response.setMessage("Actualizado");
        response.setResponse(null);

        when(inventarioService.actualizarCantidad(productoId, cantidad)).thenReturn(response);

        mockMvc.perform(put("/inventario/{id}?cantidad={cantidad}", productoId, cantidad))
                .andExpect(status().isOk());

        verify(inventarioService).actualizarCantidad(productoId, cantidad);
    }

    @Test
    void comprarProductoTest() throws Exception {
        CompraRequest request = new CompraRequest();
        request.setProductoId(1L);
        request.setCantidad(5);
        Responses response = new Responses();
        response.setError(true);
        response.setMessage("Compra realizada");
        response.setResponse(null);

        when(inventarioService.procesarCompra(request.getProductoId(), request.getCantidad())).thenReturn(response);

        mockMvc.perform(post("/inventario/comprar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(inventarioService).procesarCompra(request.getProductoId(), request.getCantidad());
    }

    @Test
    void crearInventarioTest() throws Exception {
        InventarioDTO dto = new InventarioDTO(1L, 10);
        Responses response = new Responses();
        response.setError(true);
        response.setMessage("Inventario creado");
        response.setResponse(null);

        when(inventarioService.crearInventario(dto)).thenReturn(response);

        mockMvc.perform(post("/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(inventarioService).crearInventario(dto);
    }
}