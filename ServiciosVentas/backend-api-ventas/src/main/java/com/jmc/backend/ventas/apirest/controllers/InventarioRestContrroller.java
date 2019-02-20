package com.jmc.backend.ventas.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.GeneratedValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmc.backend.ventas.apirest.models.entity.BodegaSucursal;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IBodegaSucursalService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class InventarioRestContrroller {

	@Autowired
	private IBodegaSucursalService bodegaService;
	
	@GetMapping("/inventario")
	public ResponseEntity<?> getProductosPuntoVenta(){
		Map<String, Object> response = new HashMap<>();
		try {
			List<BodegaSucursal> lsProductosBodega= bodegaService.findAll();
			if(lsProductosBodega.isEmpty()){
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<BodegaSucursal>>(lsProductosBodega, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/inventario")
	public ResponseEntity<?> asignarProducto(@RequestBody BodegaSucursal bodega){
		Map<String, Object> response = new HashMap<>();
		try {
			BodegaSucursal newRegister= null;
			newRegister= bodegaService.save(bodega);
			response.put("mensaje", "El producto a sido asignado ha sido creado con éxito!");
			response.put("Registro", newRegister);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar guardar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
