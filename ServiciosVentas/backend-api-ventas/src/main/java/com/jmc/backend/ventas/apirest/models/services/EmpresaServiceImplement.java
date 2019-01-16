package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmc.backend.ventas.apirest.models.dao.IEmpresaDao;
import com.jmc.backend.ventas.apirest.models.entity.Empresa;
import com.jmc.backend.ventas.apirest.models.entity.Modulos;

@Service
public class EmpresaServiceImplement implements IEmpresaService {

	@Autowired
	private IEmpresaDao empresaDao;
	
	@Override
	@Transactional(readOnly=true)
	public Empresa findById(Long ID) {
		// TODO Auto-generated method stub
		return empresaDao.findById(ID).orElse(null);
	}

	@Override
	@Transactional
	public Empresa save(Empresa empresa) {
		// TODO Auto-generated method stub
		return empresaDao.save(empresa);
	}
	
	

}
