package com.jmc.backend.ventas.apirest.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jmc.backend.ventas.apirest.models.entity.Empresa;
import com.jmc.backend.ventas.apirest.models.entity.Modulos;
import com.jmc.backend.ventas.apirest.models.entity.Role;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IEmpresaService;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IRoleService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class EmpresaRestController {

	@Autowired
	private IEmpresaService empresaService;
	
	@Autowired
	private IRoleService roleService;

	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/empresa/modulos/{id}")
	public List<Modulos> show(@PathVariable Long id) {
		Empresa empresa=empresaService.findById(id);
		return (List<Modulos>)empresa.getLsModulos();
	}
	
	@PostMapping("/empresa")
	@ResponseStatus(HttpStatus.CREATED)
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?>  update(@RequestBody Empresa empresa) {
		Map<String, Object> response = new HashMap<>();
		try {
			Empresa empresaActual= empresaService.findById(empresa.getId());
			empresaActual.setNombre(empresa.getNombre());
			empresaActual.setUpdatedAt(new Date());
			Empresa emptAct=empresaService.save(empresaActual);
			Empresa emprReturn= new Empresa();
			emprReturn.setNombre(emptAct.getNombre());
			emprReturn.setId(emptAct.getId());
			response.put("mensaje", "La Empresa ha sido actualizado con éxito!");
			response.put("empresa", emprReturn);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			
			response.put("mensaje", "Error al actualizar la empresa  en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("empresa/admin/{id}")
	@Secured({"ROLE_ROOT"})
	public Empresa getAllDataEmpresa(@PathVariable Long id) {
		return  empresaService.findById(id);
	}
	
	@GetMapping("empresa/roles")
	@Secured({"ROLE_ROOT"})
	public List<Role> getRoles(){
		
		return roleService.findAll();
	}
}
