package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import com.jmc.backend.ventas.apirest.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
}
