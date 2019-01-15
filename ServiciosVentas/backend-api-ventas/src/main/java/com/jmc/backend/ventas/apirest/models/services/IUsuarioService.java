package com.jmc.backend.ventas.apirest.models.services;

import com.jmc.backend.ventas.apirest.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
}
