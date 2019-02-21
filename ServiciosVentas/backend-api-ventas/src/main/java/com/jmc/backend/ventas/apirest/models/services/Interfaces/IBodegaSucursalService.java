package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.BodegaSucursal;
import com.jmc.backend.ventas.apirest.models.entity.PuntoVenta;

public interface IBodegaSucursalService {

	
	public List<BodegaSucursal> findAll();
	public List<BodegaSucursal> findAllByPuntoVenta(PuntoVenta punto);
	public BodegaSucursal save(BodegaSucursal bodega);
	public BodegaSucursal findById(Long id);
}
