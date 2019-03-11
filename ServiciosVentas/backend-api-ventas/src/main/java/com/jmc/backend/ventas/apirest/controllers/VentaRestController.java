package com.jmc.backend.ventas.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmc.backend.ventas.apirest.models.entity.Producto;
import com.jmc.backend.ventas.apirest.models.entity.PuntoVenta;
import com.jmc.backend.ventas.apirest.models.entity.Venta;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IClienteService;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IProductoService;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IVentaService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("api/")
public class VentaRestController {

	@Autowired
	private IClienteService clienteService;
	
	@Autowired 
	private IVentaService ventaService;
	
	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/ventas/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Venta venta=  clienteService.findVentaById(id);
			if(venta==null) {
				response.put("mensaje", "Venta con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Venta>(venta, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/ventas")
	public ResponseEntity<?> create(@RequestBody Venta venta) {
		Map<String, Object> response = new HashMap<>();
		try {
			
			
			Venta newVenta= clienteService.saveVenta(venta);
			response.put("mensaje", "Venta generada con éxito!");
			response.put("venta", newVenta);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/ventas")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			clienteService.deleteVentaById(id);
			response.put("mensaje", "Venta eliminada con éxito!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar venta de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/ventas/empresa/{id}")
	public ResponseEntity<?> findAllByEmpresa(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			List<Venta> lsVentas= ventaService.findAllByIdEmpresa(id);
			if(lsVentas.isEmpty()) {
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Venta>>(lsVentas, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/ventas/punto/{id}")
	public ResponseEntity<?> fidAllPuntoVenta(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			List<Venta> lsVentas= ventaService.findAllByIdPuntoVenta(id);
			if(lsVentas.isEmpty()) {
				response.put("mensaje", "No existen registros de ventas para el punto de venta con ID: "+id+ " en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Venta>>(lsVentas, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
