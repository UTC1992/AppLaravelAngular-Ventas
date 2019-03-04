package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.statics.TipoPago;

public interface ITipoPagoService {

	public List<TipoPago> findAll();
	
	public TipoPago findById(Long id);
	
	public TipoPago save(TipoPago tipoPago);
	
	public void delete(Long id);
}
