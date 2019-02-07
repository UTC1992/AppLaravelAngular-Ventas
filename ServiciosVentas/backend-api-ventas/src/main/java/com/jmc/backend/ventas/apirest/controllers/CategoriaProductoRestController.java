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
	public CategoriaProducto getById(@PathVariable Long id) {
		
		return categoriaService.findById(id);
	}
	
	@PostMapping("/categorias")
	public CategoriaProducto create(@RequestBody CategoriaProducto categoria) {
		return categoriaService.save(categoria);
	}
	
	@PutMapping("/categorias/{id}")
	public CategoriaProducto update(@RequestBody CategoriaProducto categoria, @PathVariable Long id) {
		CategoriaProducto categoriaActual= categoriaService.findById(id);
		
		categoriaActual.setNombreCategoria(categoria.getNombreCategoria());
		categoriaActual.setDescripcionCategoria(categoria.getDescripcionCategoria());
		categoriaActual.setUpdatedAt(new Date());
		return categoriaService.save(categoriaActual);
	}
	
	@DeleteMapping("/categorias/{id}")
	public Boolean delete(@PathVariable Long id) {
		
		return categoriaService.delete(id);
	}
}
