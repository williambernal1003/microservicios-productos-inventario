package com.test.inventario.inventario.dto;

import lombok.Data;

@Data
public class CompraRequest {
    private Long productoId;
    private Integer cantidad;
}