package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmc.backend.ventas.apirest.models.dao.IProvinciaDao;
import com.jmc.backend.ventas.apirest.models.entity.statics.Provincia;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IProvinciaService;

@Service
public class ProvinciaServiceImplement  implements IProvinciaService{

	@Autowired
	private IProvinciaDao ProvinciaDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Provincia> findAll() {
		// TODO Auto-generated method stub
		return ProvinciaDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Provincia findById(Long id) {
		// TODO Auto-generated method stub
		return ProvinciaDao.findById(id).orElse(null);
	}

	@Override
	public Provincia save(Provincia provincia) {
		// TODO Auto-generated method stub
		return ProvinciaDao.save(provincia);
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		 ProvinciaDao.deleteById(id);
		 return true;
	}

}
