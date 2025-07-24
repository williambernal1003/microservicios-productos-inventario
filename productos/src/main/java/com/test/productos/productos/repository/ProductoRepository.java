package com.test.productos.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.productos.productos.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

}