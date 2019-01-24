package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmc.backend.ventas.apirest.models.dao.IEmpleadoDao;
import com.jmc.backend.ventas.apirest.models.entity.Usuario;

@Service
public class EmpleadoServiceImplement implements IEmpleadoService {

	@Autowired
	private IEmpleadoDao empledoDao;
	
	@Override
	public Usuario save(Usuario usuario) {
	
		return empledoDao.save(usuario);
	}

	@Override
	public List<Usuario> findAll() {
		
		return empledoDao.findAll();
	}

	@Override
	public Usuario finById(Long id) {
		
		return empledoDao.findById(id).orElse(null);
	}

	@Override
	public Boolean delete(Long id) {
		empledoDao.deleteById(id);
		return true;
	}

}
