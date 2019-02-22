package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmc.backend.ventas.apirest.models.dao.ITipoProductoDao;
import com.jmc.backend.ventas.apirest.models.entity.statics.TipoProducto;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.ITipoProductoService;

@Service
public class TipoProductoServiceImplement implements ITipoProductoService{

	@Autowired
	private ITipoProductoDao tipoDao;
	
	@Override
	public List<TipoProducto> findAll() {
		// TODO Auto-generated method stub
		return tipoDao.findAll();
	}

}
