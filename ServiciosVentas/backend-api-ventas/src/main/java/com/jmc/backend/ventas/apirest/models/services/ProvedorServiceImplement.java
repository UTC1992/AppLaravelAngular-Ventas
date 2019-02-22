package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmc.backend.ventas.apirest.models.dao.IProvedorDao;
import com.jmc.backend.ventas.apirest.models.entity.Provedor;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IProvedorService;


@Service
public class ProvedorServiceImplement implements IProvedorService {

	@Autowired
	private IProvedorDao provedorDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Provedor> findAll(Long idEmpresa) {
		// TODO Auto-generated method stub
		return provedorDao.findAllByIdEmpresaQuery(idEmpresa);
	}

	@Override
	@Transactional
	public Provedor save(Provedor provedor) {
		// TODO Auto-generated method stub
		return provedorDao.save(provedor);
	}

	@Override
	@Transactional(readOnly=true)
	public Provedor findById(Long id) {
		// TODO Auto-generated method stub
		return provedorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Provedor findByRuc(String ruc, Long idEmpresa) {
		// TODO Auto-generated method stub
		return provedorDao.findByRucQuery(ruc, idEmpresa);
	}

	@Override
	@Transactional(readOnly=true)
	public Provedor findByCedula(String cedula, Long idEmpresa) {
		// TODO Auto-generated method stub
		return provedorDao.findByCedulaQuery(cedula, idEmpresa);
	}

	@Override
	@Transactional(readOnly=true)
	public Provedor findByEmail(String email, Long idEmpresa) {
		// TODO Auto-generated method stub
		return provedorDao.findByEmailQuery(email, idEmpresa);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		provedorDao.deleteById(id);
	}

}
