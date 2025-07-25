package com.test.inventario.inventario.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.test.inventario.inventario.util.Responses;

class GlobalExceptionHandlerTest {

	private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleNotFound() {
        NotFoundException ex = new NotFoundException();
        ResponseEntity<Responses> response = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().isError());
        assertEquals(ex.getMessage(), response.getBody().getMessage());
    }

    @Test
    void testHandleValidationErrors() {
        FieldError fieldError1 = new FieldError("object", "username", "Username is required");
        FieldError fieldError2 = new FieldError("object", "password", "Password is required");
        
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
        
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Responses> response = handler.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().isError());
        assertEquals("Error de validación de campos", response.getBody().getMessage());

        Map<String, String> errores = (Map<String, String>) response.getBody().getResponse();
        assertEquals("Username is required", errores.get("username"));
        assertEquals("Password is required", errores.get("password"));
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new RuntimeException("Fallo inesperado");
        ResponseEntity<Responses> response = handler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().isError());
        assertTrue(response.getBody().getMessage().contains("Fallo inesperado"));
    }
}