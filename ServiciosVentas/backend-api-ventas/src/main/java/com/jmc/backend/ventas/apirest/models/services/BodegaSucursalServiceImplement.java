package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmc.backend.ventas.apirest.models.dao.IBodegaSucursalDao;
import com.jmc.backend.ventas.apirest.models.entity.BodegaSucursal;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IBodegaSucursalService;

@Service
public class BodegaSucursalServiceImplement implements IBodegaSucursalService{

	@Autowired
	private IBodegaSucursalDao bodegaDao;
	
	@Override
	public List<BodegaSucursal> findAll() {
		// TODO Auto-generated method stub
		return bodegaDao.findAll();
	}

	@Override
	public BodegaSucursal save(BodegaSucursal bodega) {
		// TODO Auto-generated method stub
		return bodegaDao.save(bodega);
	}

}
