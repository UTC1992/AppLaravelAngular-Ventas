package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmc.backend.ventas.apirest.models.dao.ICategoriasDao;
import com.jmc.backend.ventas.apirest.models.entity.CategoriaProducto;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.ICategoriaProductoService;

@Service
public class CategoriaProductoServiceImplement implements ICategoriaProductoService {

	@Autowired
	private ICategoriasDao categoriasDao;
	@Override
	@Transactional
	public List<CategoriaProducto> findAll() {
		// TODO Auto-generated method stub
		
		return categoriasDao.findAll();
	}

	@Override
	@Transactional
	public CategoriaProducto findById(Long id) {
		// TODO Auto-generated method stub
		return categoriasDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public CategoriaProducto save(CategoriaProducto categoria) {
		// TODO Auto-generated method stub
		return categoriasDao.save(categoria);
	}

	@Override
	@Transactional
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		categoriasDao.deleteById(id);
		return true;
	}

}
