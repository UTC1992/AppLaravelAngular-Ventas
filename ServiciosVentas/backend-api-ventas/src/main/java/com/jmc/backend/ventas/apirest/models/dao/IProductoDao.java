package com.jmc.backend.ventas.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jmc.backend.ventas.apirest.models.entity.Producto;

public interface IProductoDao extends JpaRepository<Producto, Long> {

	@Query("select p from Producto p where p.idEmpresa=?1")
	public List<Producto> findAllByIdEmpresaQuery(Long idEmpresa);
	
	@Query("select p from Producto p where p.nombreProducto=?1 and p.idEmpresa=?2")
	public Producto findByNameQuery(String nombre, Long id);
	
	
	
	/*
	@Query("select p from Producto p where p. ")
	public List<Producto> findAllByPuntoVentaQuery(Long idPunto);*/
}
