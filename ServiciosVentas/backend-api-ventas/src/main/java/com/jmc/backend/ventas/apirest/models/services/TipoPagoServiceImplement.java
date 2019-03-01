package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmc.backend.ventas.apirest.models.dao.ITipoPagoDao;
import com.jmc.backend.ventas.apirest.models.entity.statics.TipoPago;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.ITipoPagoService;

@Service
public class TipoPagoServiceImplement implements ITipoPagoService {

	@Autowired
	private ITipoPagoDao tipoPagoDao;
	
	@Override
	public List<TipoPago> findAll() {
		// TODO Auto-generated method stub
		return tipoPagoDao.findAll();
	}

	@Override
	public TipoPago findById(Long id) {
		// TODO Auto-generated method stub
		return tipoPagoDao.findById(id).orElse(null);
	}

	@Override
	public TipoPago save(TipoPago tipoPago) {
		// TODO Auto-generated method stub
		return tipoPagoDao.save(tipoPago);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		tipoPagoDao.deleteById(id);
	}

}
