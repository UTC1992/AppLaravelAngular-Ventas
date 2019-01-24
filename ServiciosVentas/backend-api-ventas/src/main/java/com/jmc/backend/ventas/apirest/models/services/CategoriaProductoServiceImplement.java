package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmc.backend.ventas.apirest.models.dao.ICategoriasDao;
import com.jmc.backend.ventas.apirest.models.entity.CategoriaProducto;

@Service
public class CategoriaProductoServiceImplement implements ICategoriaProductoService {

	@Autowired
	private ICategoriasDao categoriasDao;
	@Override
	public List<CategoriaProducto> findAll() {
		// TODO Auto-generated method stub
		
		return categoriasDao.findAll();
	}

	@Override
	public CategoriaProducto findById(Long id) {
		// TODO Auto-generated method stub
		return categoriasDao.findById(id).orElse(null);
	}

	@Override
	public CategoriaProducto save(CategoriaProducto categoria) {
		// TODO Auto-generated method stub
		return categoriasDao.save(categoria);
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		categoriasDao.deleteById(id);
		return true;
	}

}
