package com.jmc.backend.ventas.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmc.backend.ventas.apirest.models.entity.Role;
import com.jmc.backend.ventas.apirest.models.entity.Usuario;
import com.jmc.backend.ventas.apirest.models.services.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

	@Autowired
	private UsuarioService usuario;
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/usuario/roles/{id}")
	public List<Role> getRolesUserAuth(@PathVariable Long id){
		Usuario user=usuario.findById(id);
		List<Role> lsRoles= user.getRoles();
		return lsRoles;
	}
}
