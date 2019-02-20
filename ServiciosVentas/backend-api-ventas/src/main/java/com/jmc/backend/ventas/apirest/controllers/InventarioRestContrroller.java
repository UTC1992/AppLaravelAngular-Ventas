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

import com.jmc.backend.ventas.apirest.models.entity.BodegaSucursal;
import com.jmc.backend.ventas.apirest.models.entity.PuntoVenta;
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
	
	@GetMapping("/inventario/{punto}")
	public ResponseEntity<?> getBySucursal(@RequestBody PuntoVenta punto){
		
		Map<String, Object> response = new HashMap<>();
		try{
			List<BodegaSucursal> lsProductosBodega= bodegaService.findAllByPuntoVenta(punto);
			if(lsProductosBodega.isEmpty()) {
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<BodegaSucursal>>(lsProductosBodega, HttpStatus.OK);
		}catch (DataAccessException e) {
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
	
	@GetMapping("/inventario/{id}/{cantidad}")
	public  ResponseEntity<?> actualizarInventarioSucursal(@PathVariable Long id, @PathVariable Integer cantidad){
		Map<String, Object> response = new HashMap<>();
		try {
			BodegaSucursal productoActual= bodegaService.findById(id);
			if(productoActual==null) {
				response.put("mensaje", "Registro de inventario de bodega con ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			if(cantidad<=0) {
				response.put("mensaje", "La cantidad a asignar al inventario de producto no puede ser 0 o < 0");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			Integer numActual= productoActual.getCantidad()+cantidad;
			productoActual.setCantidad(numActual);
			bodegaService.save(productoActual);
			response.put("mensaje", "El inventario de producto punto de venta a sido actualizado ha sido creado con éxito!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar actualizar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
