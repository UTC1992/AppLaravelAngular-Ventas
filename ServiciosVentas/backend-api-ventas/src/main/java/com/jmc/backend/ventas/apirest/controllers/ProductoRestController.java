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

import com.jmc.backend.ventas.apirest.models.entity.Producto;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IProductoService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class ProductoRestController {

	@Autowired
	private IProductoService productoService;
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@GetMapping("/productos/all/{id}")
	public ResponseEntity<?> index(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<Producto> lsProductos= productoService.findAll(id);
			if(lsProductos.isEmpty()) {
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Producto>>(lsProductos, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@GetMapping("/productos/{id}")
	public ResponseEntity<?> show(@PathVariable Long idProducto){
		Map<String, Object> response = new HashMap<>();
		try {
			Producto product= productoService.findById(idProducto);
			if(product==null) {
				response.put("mensaje", "Producto con ID: ".concat(idProducto.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Producto>(product, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@PostMapping("/productos")
	public ResponseEntity<?> store(@RequestBody Producto producto){
		Map<String, Object> response = new HashMap<>();
		try {
			Producto newProduct=null;
			Producto exist= productoService.findByName(producto.getNombreProducto(), producto.getIdEmpresa());
			if(exist!=null) {
				response.put("error", "Ya existe un producto con el mimso nombre en la base de datos");	
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			newProduct= productoService.save(producto);
			response.put("mensaje", "El producto ha sido creado con éxito!");
			response.put("PuntoVenta", newProduct);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al intentar guardar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"ROLE_ROOT","ROLE_ADMIN"})
	@PutMapping("/productos/{id}")
	public ResponseEntity<?> update(@RequestBody Producto producto, @PathVariable Long idProducto){
		Map<String, Object> response = new HashMap<>();
		try {
			
			Producto productoActual=productoService.findById(idProducto);
			if(productoActual==null) {
				response.put("mensaje", "Error: no se pudo editar, Producto con ID: "
						.concat(idProducto.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			productoActual.setDescripcionProducto(producto.getDescripcionProducto());
			productoActual.setUpdatedAt(new Date());
			productoActual.setCategoriaProducto(producto.getCategoriaProducto());
			productoActual.setCodigoProducto(producto.getCodigoProducto());
			productoActual.setEstadoProducto(producto.getEstadoProducto());
			productoActual.setNombreProducto(producto.getNombreProducto());
			productoActual.setObservaciones(producto.getObservaciones());
			
			Producto productoEdit= productoService.save(productoActual);
			response.put("mensaje", "Producto actualizado con éxito!");
			response.put("producto", productoEdit);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar producto en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Secured({"ROLE_ROOT","ROLE_ADMIN"})
	@DeleteMapping("/productos/{id}")
	public ResponseEntity<?> destroy(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			productoService.delete(id);
			response.put("mensaje", "Producto eliminado con éxito!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el producto de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
