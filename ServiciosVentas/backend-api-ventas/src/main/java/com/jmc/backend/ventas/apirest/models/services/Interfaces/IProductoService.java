package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.Producto;

public interface IProductoService {

	public List<Producto> findAll(Long idEmpresa);
	public Producto findById(Long id);
	public Producto save(Producto producto);
	public Boolean delete(Long id);
	
}