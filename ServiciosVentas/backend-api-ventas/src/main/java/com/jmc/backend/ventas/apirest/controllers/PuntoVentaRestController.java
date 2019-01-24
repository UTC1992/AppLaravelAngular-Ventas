package com.jmc.backend.ventas.apirest.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmc.backend.ventas.apirest.models.entity.PuntoVenta;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IPuntoVentaService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class PuntoVentaRestController {

	@Autowired
	private IPuntoVentaService puntoVenta;
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@GetMapping("/punto/all/{id}")
	public List<PuntoVenta> getAll(@PathVariable Long idEmpresa){
		return puntoVenta.findAll(idEmpresa);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@GetMapping("/punto/{id}")
	public PuntoVenta getById(@PathVariable Long id) {
		return puntoVenta.findById(id);
	}
	
	@Secured({"ROLE_ROOT"})
	@PostMapping("/punto")
	public PuntoVenta create(@RequestBody PuntoVenta puntoVenta) {
		return this.puntoVenta.save(puntoVenta);
	}
	
	@Secured({"ROLE_ROOT"})
	@PutMapping("/punto/{id}")
	public PuntoVenta update(@RequestBody PuntoVenta punto , @PathVariable Long id) {
		PuntoVenta puntoActual =  puntoVenta.findById(id);
		puntoActual.setNombre(punto.getNombre());
		puntoActual.setCiudad(punto.getCiudad());
		puntoActual.setDescripcion(punto.getDescripcion());
		puntoActual.setDireccion(punto.getDireccion());
		puntoActual.setProvincia(punto.getProvincia());
		puntoActual.setUpdatedAt(new Date());
		return puntoVenta.save(puntoActual);
	}
	
	@Secured({"ROLE_ROOT"})
	@DeleteMapping("/punto/{id}")
	public Boolean delete(@PathVariable Long id) {
		return puntoVenta.delete(id);
	}
}
