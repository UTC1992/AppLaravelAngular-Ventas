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
import com.jmc.backend.ventas.apirest.models.entity.CategoriaProducto;
import com.jmc.backend.ventas.apirest.models.entity.Empresa;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.ICategoriaProductoService;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IEmpresaService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("api/")
public class CategoriaProductoRestController {

	@Autowired 
	private ICategoriaProductoService categoriaService;
	@Autowired
	private IEmpresaService empresaService;
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/empresa/categorias/{id}")
	public ResponseEntity<?> getAll(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			Empresa empresa= empresaService.findById(id);
			List<CategoriaProducto> lsCategorias= empresa.getLsCategoriasProductos();
			if(lsCategorias.isEmpty()) {
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<CategoriaProducto>>(lsCategorias, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/categorias/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			CategoriaProducto categoria= categoriaService.findById(id);
			if(categoria==null) {
				response.put("mensaje", "Categoria con  ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<CategoriaProducto>(categoria, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@PostMapping("/categorias")
	public ResponseEntity<?> create(@RequestBody CategoriaProducto categoria) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			
			CategoriaProducto catExist= categoriaService.findByNameCategoria(categoria.getNombreCategoria(), categoria.getIdEmpresa());
			if(catExist!=null) {
				response.put("error", "Ya existe una categoria ingresada con el mismo nombre");	
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			CategoriaProducto categoriaNew= categoriaService.save(categoria);
			response.put("mensaje", "La categoria ha sido creada con éxito!");
			response.put("usuario", categoriaNew);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar categoria en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@PutMapping("/categorias/{id}")
	public ResponseEntity<?> update(@RequestBody CategoriaProducto categoria, @PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			CategoriaProducto categoriaActual=categoriaService.findById(id);
			if(categoriaActual==null) {
				response.put("mensaje", "Error: no se pudo editar, la categoria de producto con ID: "
						.concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			categoriaActual.setNombreCategoria(categoria.getNombreCategoria());
			categoriaActual.setDescripcionCategoria(categoria.getDescripcionCategoria());
			categoriaActual.setUpdatedAt(new Date());
			CategoriaProducto categoriaEditada=categoriaService.save(categoriaActual);
			response.put("mensaje", "La categoria ha sido actualizada con éxito!");
			response.put("categoria", categoriaEditada);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la categoria en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<?>  delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			categoriaService.delete(id);
			response.put("mensaje", "Usuario eliminado con éxito!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la categoria de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
