package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.Compra;

public interface ICompraService {

	public List<Compra> findAll(Long id);
	
	public Compra findById(Long id);
	
	public Compra save(Compra compra);
	
	public void delete(Long id);
	
}
