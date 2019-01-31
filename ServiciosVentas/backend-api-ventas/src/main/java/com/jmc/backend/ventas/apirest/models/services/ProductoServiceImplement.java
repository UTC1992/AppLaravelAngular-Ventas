package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmc.backend.ventas.apirest.models.dao.IProductoDao;
import com.jmc.backend.ventas.apirest.models.entity.Producto;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IProductoService;

@Service
public class ProductoServiceImplement implements IProductoService {

	@Autowired
	private IProductoDao productoDao;
	
	
	@Override
	@Transactional(readOnly=true)
	public List<Producto> findAll(Long idEmpresa) {
		// TODO Auto-generated method stub
		return productoDao.findAllByIdEmpresaQuery(idEmpresa);
	}

	@Override
	@Transactional(readOnly= true)
	public Producto findById(Long id) {
		// TODO Auto-generated method stub
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Producto save(Producto producto) {
		// TODO Auto-generated method stub
		return productoDao.save(producto);
	}

	@Override
	@Transactional
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		productoDao.deleteById(id);
		return true;
	}

}
