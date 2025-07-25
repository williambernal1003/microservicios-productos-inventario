package com.test.productos.productos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventarioDTO {
	@NotNull(message = "El productoId no puede ser nulo")
	private Long productoId;
	
	@Min(value = 1, message = "La cantidad debe ser mayor o igual a 1")
    private int cantidad;
}