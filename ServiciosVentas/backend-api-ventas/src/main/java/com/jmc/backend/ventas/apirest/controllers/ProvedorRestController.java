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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.jmc.backend.ventas.apirest.models.entity.Provedor;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IProvedorService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class ProvedorRestController {

	@Autowired
	private IProvedorService provedorService;
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@GetMapping("/provedores/all/{id}")
	public ResponseEntity<?> index(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			List<Provedor> lsProvedores= provedorService.findAll(id);
			if(lsProvedores.isEmpty()) {
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Provedor>>(lsProvedores, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@GetMapping("/provedores/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			Provedor provedor= provedorService.findById(id);
			if(provedor==null) {
				response.put("mensaje", "Provedor con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Provedor>(provedor, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@PostMapping("/provedores")
	public ResponseEntity<?> create(@RequestBody Provedor provedor){
		Map<String, Object> response = new HashMap<>();
		try {
			Provedor exist= null;
			exist= provedorService.findByCedula(provedor.getCedula(), provedor.getIdEmpresa());
			if(exist!=null) {
				response.put("mensaje",
						"Provedor con  Cedula: ".concat(provedor.getCedula().toString().concat(" ya existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}	
			exist=null;
			exist = provedorService.findByEmail(provedor.getEmail(), provedor.getIdEmpresa());
			if(exist!=null) {
				response.put("mensaje",
						"Provedor con  Email: ".concat(provedor.getCedula().toString().concat(" ya existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			exist=null;
			exist = provedorService.findByRuc(provedor.getRuc(), provedor.getIdEmpresa());
			if(exist!=null) {
				response.put("mensaje",
						"Provedor con  Ruc: ".concat(provedor.getCedula().toString().concat(" ya existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			Provedor newProvedor= provedorService.save(provedor);
			response.put("mensaje", "El provedor ha sido creada con éxito!");
			response.put("provedor", newProvedor);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar provedor en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@PutMapping("/provedores/{id}")
	public ResponseEntity<?> update(@RequestBody Provedor provedor, @PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			Provedor provedorActual= provedorService.findById(id);
			if(provedorActual==null) {
				response.put("mensaje", "Error: no se pudo editar, el provedor con ID: "
						.concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			provedorActual.setCedula(provedor.getCedula());
			provedorActual.setNombre(provedor.getNombre());
			provedorActual.setApellido(provedor.getApellido());
			provedorActual.setCelular(provedor.getCelular());
			provedorActual.setCiudad(provedor.getCiudad());
			provedorActual.setProvincia(provedor.getProvincia());
			provedorActual.setDireccion1(provedor.getDireccion1());
			provedorActual.setDireccion2(provedor.getDireccion2());
			provedorActual.setCuenta1(provedor.getCuenta1());
			provedorActual.setCuenta2(provedor.getCuenta2());
			provedorActual.setEmail(provedor.getEmail());
			provedorActual.setEstado(provedor.getEstado());
			provedorActual.setObservacion(provedor.getObservacion());
			provedorActual.setUpdatedAt(new Date());
			Provedor newProvedor= provedorService.save(provedorActual);
			response.put("mensaje", "El provedor ha sido actualizada con éxito!");
			response.put("provedor", newProvedor);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el provedor en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@DeleteMapping("/provedores/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			provedorService.delete(id);
			response.put("mensaje", "Provedor eliminado con éxito!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el provedor de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
