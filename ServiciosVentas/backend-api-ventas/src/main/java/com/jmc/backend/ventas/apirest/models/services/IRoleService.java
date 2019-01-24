package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.Role;

public interface IRoleService {

	public List<Role> findAll();
	public Role findById(Long id);
	
}
