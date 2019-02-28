package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmc.backend.ventas.apirest.models.dao.IClienteDao;
import com.jmc.backend.ventas.apirest.models.entity.Cliente;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IClienteService;


@Service
public class ClienteServiceImplement implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;
	
	
	@Override
	@Transactional(readOnly=true)
	public List<Cliente> findAll(Long idEmpresa) {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findByEmpresaQuery(idEmpresa);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}
	@Override
	@Transactional(readOnly=true)
	public Cliente findById(Long ID) {
		// TODO Auto-generated method stub
		return clienteDao.findById(ID).orElse(null);
	}


	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		// TODO Auto-generated method stub
		return clienteDao.save(cliente);
	}


	@Override
	@Transactional
	public void delete(Long ID) {
		// TODO Auto-generated method stub
		clienteDao.deleteById(ID);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findByEmail(String email, Long idEmpresa) {
		// TODO Auto-generated method stub
		return clienteDao.findByEmailQuery(email, idEmpresa);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findByCedula(String cedula, Long idEmpresa) {
		// TODO Auto-generated method stub
		return clienteDao.findByCedulaQuery(cedula, idEmpresa);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findByRuc(String ruc, Long idEmpresa) {
		// TODO Auto-generated method stub
		return clienteDao.findByRucQuery(ruc, idEmpresa);
	}

	
}
