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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmc.backend.ventas.apirest.models.entity.Empresa;
import com.jmc.backend.ventas.apirest.models.entity.Role;
import com.jmc.backend.ventas.apirest.models.entity.Usuario;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IEmpleadoService;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IEmpresaService;


@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class EmpleadoRestController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IEmpresaService empresaService;
	
	@Autowired
	private IEmpleadoService empleadoService;

	
	@GetMapping("/empresa/empleado/{id}")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> getUserByEmpresa(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try {
			Empresa empresa= empresaService.findById(id);
			List<Usuario> lsUsuarios= empresa.getUsuarios();
			if(lsUsuarios.isEmpty()) {
				response.put("mensaje", "No existen registros en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<Usuario>>(lsUsuarios, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@Secured({"ROLE_ADMIN"})
	@GetMapping("empleado/{id}")
	public ResponseEntity<?>  getById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Usuario user=null;
			user= empleadoService.finById(id);
			if(user == null) {
				response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Usuario>(user, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("rol/asignar/{id}")
	public ResponseEntity<?> asignRoleUser(@RequestBody Role rol, @PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			Usuario user= empleadoService.finById(id);
			user.getRoles().add(rol);
			user.setUpdatedAt(new Date());
			Usuario empleado=empleadoService.save(user);
			response.put("mensaje", "Rol asignado con éxito!");
			response.put("Empleado", empleado);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al asignar el rol al empleado");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("rol/eliminar/{id}")
	@Secured({"ROLE_ROOT"})
	public Boolean cleanRoles(@PathVariable Long id) {
		Usuario user = empleadoService.finById(id);
		user.getRoles().clear();
		empleadoService.save(user);
		return true;
	}
	
	@PostMapping("/empleado")
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	public  ResponseEntity<?> createUser(@RequestBody Usuario usuario) {
		Map<String, Object> response = new HashMap<>();
			
		try {
			Usuario userNew=null;
			Usuario user= empleadoService.findByUsername(usuario.getUsername(),usuario.getIdEmpresa());
			Usuario userEmail= empleadoService.findByEmail(usuario.getEmail());
			if(user!=null) {
				response.put("error", "Ya existe el usuario registrado en la base de datos");	
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}else if(userEmail!=null) {
				response.put("error", "Ya existe un usuario  registrado con el email ingresado: ".concat(userEmail.getEmail()));	
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			String passEncrypt=passwordEncoder.encode(usuario.getPassword());
			usuario.setPassword(passEncrypt);
			userNew= empleadoService.save(usuario);
			response.put("mensaje", "El Usuario ha sido creado con éxito!");
			response.put("usuario", userNew);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		
	}
	
	@PutMapping("/empleado/{id}")
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	public ResponseEntity<?> updateEmpleado(@RequestBody Usuario usuario,@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Usuario usuarioActual= empleadoService.finById(id); 
			if(usuarioActual==null) {
				response.put("mensaje", "Error: no se pudo editar, el usuario con ID: "
						.concat(id.toString().concat(" no existe en la base de datos!")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			usuarioActual.setNombre(usuario.getNombre());
			usuarioActual.setApellido(usuario.getApellido());
			usuarioActual.setEmail(usuario.getEmail());
			usuarioActual.setDni(usuario.getDni());
			usuarioActual.setUsername(usuario.getUsername());
			usuarioActual.setCelular(usuario.getCelular());
			usuarioActual.setDni(usuario.getDni());
			usuarioActual.setCarnet(usuario.getCarnet());
			usuarioActual.setDiscapacidad(usuario.getDiscapacidad());
			usuarioActual.setDireccion(usuario.getDireccion());
			usuarioActual.setEdad(usuario.getEdad());
			usuarioActual.setEnabled(usuario.getEnabled());
			usuarioActual.setFechaNacimiento(usuario.getFechaNacimiento());
			usuarioActual.setGenero(usuario.getGenero());
			usuarioActual.setUpdatedAt(new Date());
			Usuario usuarioEditado=empleadoService.save(usuarioActual);
			response.put("mensaje", "El usuario ha sido actualizado con éxito!");
			response.put("usuario", usuarioEditado);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el usuario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

	}
	
	@DeleteMapping("/empleado/{id}")
	@Secured({"ROLE_ADMIN","ROLE_ROOT"})
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			empleadoService.delete(id);
			response.put("mensaje", "Usuario eliminado con éxito!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el usuario de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
