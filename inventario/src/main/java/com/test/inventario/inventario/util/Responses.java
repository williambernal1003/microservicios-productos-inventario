package com.test.inventario.inventario.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Responses {
    private Object response;
    private boolean error;
    private String message;
}