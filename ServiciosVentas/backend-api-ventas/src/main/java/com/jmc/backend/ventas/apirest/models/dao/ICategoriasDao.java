package com.jmc.backend.ventas.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jmc.backend.ventas.apirest.models.entity.CategoriaProducto;

public interface ICategoriasDao extends JpaRepository<CategoriaProducto, Long> {

	@Query("select c from CategoriaProducto c where c.nombreCategoria=?1 and c.idEmpresa=?2")
	public CategoriaProducto findByNombreCategoriaQuery(String nombreCategoria,Long idEmpresa);
	
}
