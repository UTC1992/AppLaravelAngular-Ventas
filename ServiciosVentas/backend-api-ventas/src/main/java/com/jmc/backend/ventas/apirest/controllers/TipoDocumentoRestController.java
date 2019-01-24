package com.jmc.backend.ventas.apirest.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.jmc.backend.ventas.apirest.models.services.ITipoDocumentoService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("api/")
public class TipoDocumentoRestController {

	@Autowired
	private ITipoDocumentoService tipoDocumentoService;
	
	@GetMapping("/empresa/tipo/{id}")
	public List<TipoDocumento> getAll(@PathVariable Long id){
		return tipoDocumentoService.findAll(id);
	}
	
	@GetMapping("tipo/{id}")
	public TipoDocumento getById(@PathVariable Long id) {
		return tipoDocumentoService.findById(id);
	}
	
	@PostMapping("/tipo")
	public TipoDocumento create(@RequestBody TipoDocumento tipo) {
		
		return tipoDocumentoService.save(tipo);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("tipo/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public TipoDocumento update(@RequestBody TipoDocumento tipo ,@PathVariable Long id) {
		TipoDocumento tipoActual= tipoDocumentoService.findById(id);
		
		tipoActual.setNombreDocumento(tipo.getNombreDocumento());
		tipoActual.setDescripcion(tipo.getDescripcion());
		tipoActual.setUpdatedAt(new Date());
		return tipoDocumentoService.save(tipoActual);
	}
	
	@DeleteMapping("tipo/{id}")
	public Boolean delete(@PathVariable Long id) {
		return tipoDocumentoService.delete(id);
	}
}
