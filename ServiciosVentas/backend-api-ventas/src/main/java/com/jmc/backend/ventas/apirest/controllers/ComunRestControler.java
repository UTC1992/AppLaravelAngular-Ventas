package com.jmc.backend.ventas.apirest.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmc.backend.ventas.apirest.models.entity.TipoProducto;
import com.jmc.backend.ventas.apirest.models.entity.Usuario;
import com.jmc.backend.ventas.apirest.models.entity.statics.Provincia;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IProvinciaService;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.ITipoProductoService;


@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class ComunRestControler {

	@Autowired
	private IProvinciaService provinciaService;
	@Autowired
	private ITipoProductoService tipoProductoService;
	
	@GetMapping("provincias")
	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	public ResponseEntity<?> getProvicias(){
		Map<String, Object> response = new HashMap<>();
		try {
			List<Provincia> lsProvincias= provinciaService.findAll();
			if(lsProvincias.isEmpty()) {
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Provincia>>(lsProvincias, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("provincias/{id}")
	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	public ResponseEntity<?> getById(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			Provincia provincia= null;
			 provincia= provinciaService.findById(id);
			if(provincia==null) {
				response.put("mensaje", "La Provincia con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Provincia>(provincia, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/tipo_producto")
	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	public ResponseEntity<?> getTipoProducto(){
		Map<String, Object> response = new HashMap<>();
		try {
			List<TipoProducto> lsTipoProducto= tipoProductoService.findAll();
			if(lsTipoProducto.isEmpty()) {
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<TipoProducto>>(lsTipoProducto, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
