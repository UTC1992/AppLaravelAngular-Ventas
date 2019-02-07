package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.Usuario;

public interface IEmpleadoService {

	public Usuario save(Usuario usuario);
	public List<Usuario> findAll();
	public Usuario finById(Long id);
	public Boolean delete(Long id);
	public Usuario findByUsername(String username, Long idEmpresa);
	public Usuario findByEmail(String email);
}
