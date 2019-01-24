package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.Usuario;

public interface IEmpleadoService {

	public Usuario save(Usuario usuario);
	public List<Usuario> findAll();
	public Usuario finById(Long id);
	public Boolean delete(Long id);
}
