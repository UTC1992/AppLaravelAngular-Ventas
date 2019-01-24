package com.jmc.backend.ventas.apirest.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.jmc.backend.ventas.apirest.models.services.ICategoriaProductoService;
import com.jmc.backend.ventas.apirest.models.services.IEmpresaService;

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
	public List<CategoriaProducto> getAll(@PathVariable Long id){
		
		Empresa empresa= empresaService.findById(id);
		return empresa.getLsCategoriasProductos();
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
