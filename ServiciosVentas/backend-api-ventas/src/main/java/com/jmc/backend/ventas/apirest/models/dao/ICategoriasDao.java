package com.jmc.backend.ventas.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmc.backend.ventas.apirest.models.entity.CategoriaProducto;

public interface ICategoriasDao extends JpaRepository<CategoriaProducto, Long> {

	
}
