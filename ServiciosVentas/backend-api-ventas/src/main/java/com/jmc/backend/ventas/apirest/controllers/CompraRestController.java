package com.jmc.backend.ventas.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmc.backend.ventas.apirest.models.entity.Compra;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.ICompraService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class CompraRestController {
	
	@Autowired
	private ICompraService comprasService;
	
	@GetMapping("/compras/all/{id}")
	public ResponseEntity<?> index(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			
			List<Compra> lsCompras= comprasService.findAll(id);
			if(lsCompras.isEmpty()) {
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Compra>>(lsCompras, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/compras/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			Compra compra= comprasService.findById(id);
			if(compra==null) {
				response.put("mensaje", "Compra con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Compra>(compra, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/compras")
	public ResponseEntity<?> create(@RequestBody Compra compra){
		Map<String, Object> response = new HashMap<>();
		try {
			Compra newCompra = comprasService.save(compra);
			response.put("mensaje", "Venta generada con éxito!");
			response.put("compra", newCompra);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			comprasService.delete(id);
			response.put("mensaje", "Compra eliminada con éxito!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar venta de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
