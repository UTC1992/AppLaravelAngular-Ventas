package com.jmc.backend.ventas.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jmc.backend.ventas.apirest.models.entity.BodegaSucursal;
import com.jmc.backend.ventas.apirest.models.entity.PuntoVenta;

public interface IBodegaSucursalDao extends JpaRepository<BodegaSucursal, Long> {

	
	@Query("select b from BodegaSucursal b where b.punto=?1")
	public List<BodegaSucursal> findByPuntoVentaQuery(PuntoVenta punto);
	
}
