package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.BodegaSucursal;

public interface IBodegaSucursalService {

	
	public List<BodegaSucursal> findAll();
	
	public BodegaSucursal save(BodegaSucursal bodega);
	
}
