package com.jmc.backend.ventas.apirest.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jmc.backend.ventas.apirest.models.entity.Empresa;
import com.jmc.backend.ventas.apirest.models.entity.Modulos;
import com.jmc.backend.ventas.apirest.models.services.IEmpresaService;

@RestController
@RequestMapping("/api")
public class EmpresaRestController {

	@Autowired
	private IEmpresaService empresaService;
	

	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/empresa/modulos/{id}")
	public List<Modulos> show(@PathVariable Long id) {
		Empresa empresa=empresaService.findById(id);
		return (List<Modulos>)empresa.getLsModulos();
	}
	
	@PostMapping("/empresa")
	@ResponseStatus(HttpStatus.CREATED)
	@Secured({"ROLE_ADMIN"})
	public Empresa update(@RequestBody Empresa empresa) {
		
		Empresa empresaActual= empresaService.findById(empresa.getId());
		empresaActual.setNombre(empresa.getNombre());
		empresaActual.setUpdatedAt(new Date());
		Empresa emptAct=empresaService.save(empresaActual);
		Empresa emprReturn= new Empresa();
		emprReturn.setNombre(emptAct.getNombre());
		emprReturn.setId(emptAct.getId());
		return emprReturn;
	}
	
	@GetMapping("empresa/admin/{id}")
	@Secured({"ROLE_ROOT"})
	public Empresa getAllDataEmpresa(@PathVariable Long id) {
		return  empresaService.findById(id);
	}
}
