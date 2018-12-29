package com.jmc.backend.ventas.apirest.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jmc.backend.ventas.apirest.models.entity.Cliente;
import com.jmc.backend.ventas.apirest.models.services.IClienteService;

@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> index(){
		return clienteService.findAll();
	}
	
	@GetMapping("clientes/{id}")
	public Cliente show(@PathVariable Long id) {
		return clienteService.findById(id);
	}
	
	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente create(@RequestBody Cliente cliente) {
		return clienteService.save(cliente);
	}
	
	@PutMapping("clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update(@RequestBody Cliente cliente,@PathVariable Long id) {
		Cliente clienteActual=clienteService.findById(id);
		
		clienteActual.setNombres(cliente.getNombres());
		clienteActual.setApellidos(cliente.getApellidos());
		clienteActual.setCedula(cliente.getCedula());
		clienteActual.setDireccion(cliente.getDireccion());
		clienteActual.setCiudad(cliente.getCiudad());
		clienteActual.setRuc(cliente.getRuc());
		clienteActual.setEmail(cliente.getEmail());
		clienteActual.setTelefono(cliente.getTelefono());
		clienteActual.setProvincia(cliente.getProvincia());
		clienteActual.setObservaciones(cliente.getObservaciones());
		clienteActual.setUpdatedAt(new Date());
		
		return clienteService.save(clienteActual);
		
	}
	
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}
}
