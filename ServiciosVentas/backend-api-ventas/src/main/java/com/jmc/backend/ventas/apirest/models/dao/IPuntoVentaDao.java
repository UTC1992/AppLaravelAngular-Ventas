package com.jmc.backend.ventas.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jmc.backend.ventas.apirest.models.entity.PuntoVenta;

public interface IPuntoVentaDao extends JpaRepository<PuntoVenta, Long> {

	@Query("select p from PuntoVenta p where p.idEmpresa=?1")
	public List<PuntoVenta> findAllByIdEmpresaQuery(Long idEmpresa);
	
	@Query("select p from PuntoVenta p where p.nombre=?1 and p.idEmpresa?2")
	public PuntoVenta findByName(String nombre,Long id);
}
