package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.PuntoVenta;

public interface IPuntoVentaService {

	public List<PuntoVenta> findAll(Long idEmpresa);
	public PuntoVenta findById(Long id);
	public PuntoVenta save(PuntoVenta puntoVenta);
	public Boolean delete(Long id);
	public PuntoVenta findByName(String name,Long id);
}
