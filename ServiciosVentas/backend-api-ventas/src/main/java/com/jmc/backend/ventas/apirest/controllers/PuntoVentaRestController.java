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
import com.jmc.backend.ventas.apirest.models.entity.PuntoVenta;
import com.jmc.backend.ventas.apirest.models.entity.Usuario;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IPuntoVentaService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class PuntoVentaRestController {

	@Autowired
	private IPuntoVentaService puntoVenta;
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@GetMapping("/punto/all/{id}")
	public ResponseEntity<?>  getAll(@PathVariable Long idEmpresa){
		
		Map<String, Object> response = new HashMap<>();
		try {
			List<PuntoVenta> lsPuntos= puntoVenta.findAll(idEmpresa);
			if(lsPuntos.isEmpty()) {
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<PuntoVenta>>(lsPuntos, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@GetMapping("/punto/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		Map<String,Object> response= new HashMap<>();
		try {
		PuntoVenta puntoFind= puntoVenta.findById(id);
		if(puntoFind==null) {
			response.put("mensaje", "Punto de venta con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PuntoVenta>(puntoFind, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@Secured({"ROLE_ROOT"})
	@PostMapping("/punto")
	public ResponseEntity<?> create(@RequestBody PuntoVenta puntoVenta) {
		Map<String, Object> response = new HashMap<>();
		try {
			PuntoVenta newPunto= null;
			PuntoVenta exist= this.puntoVenta.findByName(puntoVenta.getNombre(),puntoVenta.getId());
			if(exist!=null) {
				response.put("error", "Punto de venta ya existe en la base de datos");	
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			newPunto= this.puntoVenta.save(puntoVenta);
			response.put("mensaje", "Punto de venta ha sido creado con éxito!");
			response.put("PuntoVenta", newPunto);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"ROLE_ROOT"})
	@PutMapping("/punto/{id}")
	public ResponseEntity<?> update(@RequestBody PuntoVenta punto , @PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			
			PuntoVenta puntoActual =  puntoVenta.findById(id);
			if(puntoActual==null) {
				response.put("mensaje", "Error: no se pudo editar, punto de venta con ID: "
						.concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			puntoActual.setNombre(punto.getNombre());
			puntoActual.setCiudad(punto.getCiudad());
			puntoActual.setDescripcion(punto.getDescripcion());
			puntoActual.setDireccion(punto.getDireccion());
			puntoActual.setProvincia(punto.getProvincia());
			puntoActual.setUpdatedAt(new Date());
			PuntoVenta puntoEditado=puntoVenta.save(puntoActual);
			response.put("mensaje", "Punto de venta ha sido actualizado con éxito!");
			response.put("PuntoVenta", puntoEditado);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el punto de venta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Secured({"ROLE_ROOT"})
	@DeleteMapping("/punto/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			puntoVenta.delete(id);
			response.put("mensaje", "Punto de venta eliminado con éxito!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el punto de venta de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
