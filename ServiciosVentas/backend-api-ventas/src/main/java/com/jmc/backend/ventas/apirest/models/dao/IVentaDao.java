package com.jmc.backend.ventas.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jmc.backend.ventas.apirest.models.entity.Venta;

public interface IVentaDao extends CrudRepository<Venta, Long>{

	@Query("select v from Venta v where v.puntoVentaId=?1")
	public List<Venta> findAllByIdPuntoVentaQuery(Long idPuntoVenta);
	
	@Query("select v from Venta v where v.idEmpresa=?1")
	public List<Venta> findAllByIdEmpresaQuery(Long idEmpresa);
}
