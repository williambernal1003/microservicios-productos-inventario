package com.test.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.inventario.inventario.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}