package com.jmc.backend.ventas.apirest.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmc.backend.ventas.apirest.models.entity.Empresa;
import com.jmc.backend.ventas.apirest.models.entity.Role;
import com.jmc.backend.ventas.apirest.models.entity.Usuario;
import com.jmc.backend.ventas.apirest.models.services.IEmpleadoService;
import com.jmc.backend.ventas.apirest.models.services.IEmpresaService;
import com.jmc.backend.ventas.apirest.models.services.IRoleService;
import com.jmc.backend.ventas.apirest.models.services.UsuarioServiceImplement;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class EmpleadoRestController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IEmpresaService empresaService;
	
	@Autowired
	private IEmpleadoService empleadoService;
	@Autowired
	private IRoleService roleService;
	
	@GetMapping("/empresa/empleado/{id}")
	@Secured({"ROLE_ADMIN"})
	public List<Usuario> getUserByEmpresa(@PathVariable Long id){
		Empresa empresa= empresaService.findById(id);
		List<Usuario> lsUsuarios= empresa.getUsuarios();
		return lsUsuarios;
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("empleado/{id}")
	public Usuario getById(@PathVariable Long id) {
		
		return empleadoService.finById(id);
	}
	
	@PostMapping("/empleado")
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	public Usuario createUser(@RequestBody Usuario usuario) {
		Long idRol=(long) 1;
		String passEncrypt=passwordEncoder.encode(usuario.getPassword());
		List<Role> lsRoles= usuario.getRoles();
		usuario.getRoles().add(roleService.findById(idRol));
		usuario.setPassword(passEncrypt);
		return empleadoService.save(usuario);
	}
	
	@PutMapping("/empleado/{id}")
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	public Usuario updateEmpleado(@RequestBody Usuario usuario,@PathVariable Long id) {
		
		Usuario usuarioActual= empleadoService.finById(id); 
		
		usuarioActual.setNombre(usuario.getNombre());
		usuarioActual.setApellido(usuario.getApellido());
		usuarioActual.setEmail(usuario.getEmail());
		usuarioActual.setDni(usuario.getDni());
		usuarioActual.setRoles(usuario.getRoles());
		usuarioActual.setUpdatedAt(new Date());
		
		return empleadoService.save(usuarioActual);

	}
	
	@DeleteMapping("/empleado/{id}")
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	public Boolean delete(@PathVariable Long id) {
		return empleadoService.delete(id);
	}
}
