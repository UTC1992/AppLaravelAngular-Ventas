package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.CategoriaProducto;

public interface ICategoriaProductoService {

	public List<CategoriaProducto> findAll();
	public CategoriaProducto findById(Long id);
	public CategoriaProducto save(CategoriaProducto categoria);
	public Boolean delete(Long id);
	public CategoriaProducto findByNameCategoria(String nombre, Long idEmpresa);
}
