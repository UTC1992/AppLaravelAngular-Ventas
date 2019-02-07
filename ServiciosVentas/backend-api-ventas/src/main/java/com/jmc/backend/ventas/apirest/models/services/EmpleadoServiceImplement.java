package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmc.backend.ventas.apirest.models.dao.IEmpleadoDao;
import com.jmc.backend.ventas.apirest.models.entity.Usuario;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IEmpleadoService;

@Service
public class EmpleadoServiceImplement implements IEmpleadoService {

	@Autowired
	private IEmpleadoDao empledoDao;
	
	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
	
		return empledoDao.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		
		return empledoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario finById(Long id) {
		
		return empledoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Boolean delete(Long id) {
		empledoDao.deleteById(id);
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username, Long idEmpresa) {
		// TODO Auto-generated method stub
		return empledoDao.findByUsernameQuery(username,idEmpresa);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByEmail(String email) {
		// TODO Auto-generated method stub
		return empledoDao.findEmailQuery(email);
	}

}
