package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmc.backend.ventas.apirest.models.dao.IComprasDao;
import com.jmc.backend.ventas.apirest.models.entity.Compra;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.ICompraService;

@Service
public class CompraServiceImplement implements ICompraService{

	@Autowired
	private IComprasDao comprasDao;
	
	@Override
	public List<Compra> findAll(Long id) {
		// TODO Auto-generated method stub
		return comprasDao.findAllByEmpresaQuery(id);
	}

	@Override
	public Compra findById(Long id) {
		// TODO Auto-generated method stub
		return comprasDao.findById(id).orElse(null);
	}

	@Override
	public Compra save(Compra compra) {
		// TODO Auto-generated method stub
		return comprasDao.save(compra);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		comprasDao.deleteById(id);
	}

	
}
