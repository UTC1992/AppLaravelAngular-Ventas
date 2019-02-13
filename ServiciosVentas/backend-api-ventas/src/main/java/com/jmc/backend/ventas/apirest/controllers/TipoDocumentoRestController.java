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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jmc.backend.ventas.apirest.models.entity.TipoDocumento;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.ITipoDocumentoService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("api/")
public class TipoDocumentoRestController {

	@Autowired
	private ITipoDocumentoService tipoDocumentoService;
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@GetMapping("/empresa/tipo/{id}")
	public ResponseEntity<?> getAll(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			List<TipoDocumento> lsDocumentosTipo= tipoDocumentoService.findAll(id);
			if(lsDocumentosTipo.isEmpty()) {
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<TipoDocumento>>(lsDocumentosTipo, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@GetMapping("tipo/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			TipoDocumento tipo= tipoDocumentoService.findById(id);
			if(tipo==null) {
				response.put("mensaje", "Tipo de documento con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<TipoDocumento>(tipo, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@PostMapping("/tipo")
	public ResponseEntity<?> create(@RequestBody TipoDocumento tipo) {
		Map<String, Object> response = new HashMap<>();
		try {
			TipoDocumento newTipo= null;
			TipoDocumento exist= tipoDocumentoService.findByName(tipo.getNombreDocumento(), tipo.getIdEmpresa());
			if(exist!=null) {
				response.put("error", "Ya has registrado un tipo de documento con el mismo nombre en la base de datos");	
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			newTipo= tipoDocumentoService.save(tipo);
			response.put("mensaje", "Tipo de documento ha sido creado con éxito!");
			response.put("TipoDocumento", newTipo);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("tipo/{id}")
	public ResponseEntity<?> update(@RequestBody TipoDocumento tipo ,@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			TipoDocumento tipoActual= tipoDocumentoService.findById(id);
			if(tipoActual==null) {
				response.put("mensaje", "Error: no se pudo editar, tipo de docuemnto con ID: "
						.concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			tipoActual.setNombreDocumento(tipo.getNombreDocumento());
			tipoActual.setDescripcion(tipo.getDescripcion());
			tipoActual.setUpdatedAt(new Date());
			TipoDocumento tipoEditado= tipoDocumentoService.save(tipoActual);
			response.put("mensaje", "Tipo de documento ha sido actualizado con éxito!");
			response.put("TipoDocumento", tipoEditado);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el tipo de documento en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@DeleteMapping("tipo/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			tipoDocumentoService.delete(id);
			response.put("mensaje", "Tipo de documento eliminado con éxito!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el tipo de documento de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
