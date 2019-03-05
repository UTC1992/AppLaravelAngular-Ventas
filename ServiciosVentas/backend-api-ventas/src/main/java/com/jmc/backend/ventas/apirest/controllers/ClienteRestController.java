package com.jmc.backend.ventas.apirest.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


import com.jmc.backend.ventas.apirest.models.entity.Cliente;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IClienteService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;

	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@GetMapping("/clientes/all/{id}")
	public ResponseEntity<?>  getAll(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<Cliente> lsClientes = clienteService.findAll(id);
			if (lsClientes.isEmpty()) {
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Cliente>>(lsClientes, HttpStatus.OK);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return clienteService.findAll(pageable);
	}


	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Cliente cliente = clienteService.findById(id);
			if (cliente == null) {
				response.put("mensaje",
						"Cliente con  ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {
		Map<String, Object> response = new HashMap<>();
		try {
			Cliente exist=null;
			exist= clienteService.findByEmail(cliente.getEmail(), cliente.getIdEmpresa());
			if(exist!=null) {
				response.put("mensaje",
						"Cliente con  Email: ".concat(cliente.getEmail().toString().concat(" ya existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			exist=null;
			exist= clienteService.findByCedula(cliente.getCedula(), cliente.getIdEmpresa());
			if(exist!=null) {
				response.put("mensaje",
						"Cliente con  Cédula: ".concat(cliente.getCedula().toString().concat(" ya existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			exist=null;
			exist= clienteService.findByRuc(cliente.getRuc(), cliente.getIdEmpresa());
			if(exist!=null) {
				response.put("mensaje",
						"Cliente con  Ruc: ".concat(cliente.getRuc().toString().concat(" ya existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			Cliente newCliente= clienteService.save(cliente);
			response.put("mensaje", "El cliente ha sido creada con éxito!");
			response.put("cliente", newCliente);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al insertar cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@PutMapping("clientes/{id}")
	public ResponseEntity<?> update(@RequestBody Cliente cliente,@PathVariable Long id)
	{
		Map<String, Object> response = new HashMap<>();
		try {
			Cliente clienteActual = clienteService.findById(id);
			if(clienteActual==null) {
				response.put("mensaje", "Error: no se pudo editar, el cliente con ID: "
						.concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			clienteActual.setNombres(cliente.getNombres());
			clienteActual.setApellidos(cliente.getApellidos());
			clienteActual.setCedula(cliente.getCedula());
			clienteActual.setDireccion(cliente.getDireccion());
			clienteActual.setCiudad(cliente.getCiudad());
			clienteActual.setRuc(cliente.getRuc());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setTelefono(cliente.getTelefono());
			clienteActual.setIdEmpresa(cliente.getIdEmpresa());
			clienteActual.setSitioWeb(cliente.getSitioWeb());
			clienteActual.setProvincia(cliente.getProvincia());
			clienteActual.setObservaciones(cliente.getObservaciones());
			clienteActual.setUpdatedAt(new Date());
			Cliente clienteEditado=clienteService.save(clienteActual);
			response.put("mensaje", "La categoria ha sido actualizada con éxito!");
			response.put("categoria", clienteEditado);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

	}

	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@DeleteMapping("/clientes/{id}")
	public  ResponseEntity<?> delete(@PathVariable Long id)
	{
		Map<String, Object> response = new HashMap<>();
		try {
			clienteService.delete(id);
			response.put("mensaje", "Cliente eliminado con éxito!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	/**
	 * busqueda en la base de datos por cédula
	 * @param cedula
	 * @param id
	 * @return
	 */
	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@GetMapping("/clientes/cedula/{cedula}/{id}")
	public ResponseEntity<?> findByCedula(@PathVariable String cedula,@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			Cliente cliente= clienteService.findByCedula(cedula, id);
			if(cliente== null) {
				response.put("mensaje",
						"Cliente con  CÉDULA: ".concat(cedula.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Busqueda en la base  por ruc de cliente
	 * @param ruc
	 * @param id
	 * @return
	 */
	@Secured({"ROLE_ADMIN","ROLE_ROOT","ROLE_USER"})
	@GetMapping("/clientes/ruc/{ruc}/{id}")
	public ResponseEntity<?> findByRuc(@PathVariable String ruc,@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			Cliente cliente= clienteService.findByRuc(ruc, id);
			if(cliente== null) {
				response.put("mensaje",
						"Cliente con  RUC: ".concat(ruc.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
