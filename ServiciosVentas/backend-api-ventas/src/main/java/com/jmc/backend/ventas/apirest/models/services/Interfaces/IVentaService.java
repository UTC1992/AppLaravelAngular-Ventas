package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.Venta;

public interface IVentaService {

	
	public List<Venta> findAllByIdEmpresa(Long idEmpresa);
	
	public List<Venta> findAllByIdPuntoVenta(Long idPunto);
	
		
	
}
