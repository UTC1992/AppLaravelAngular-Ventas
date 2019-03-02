package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmc.backend.ventas.apirest.models.dao.IVentaDao;
import com.jmc.backend.ventas.apirest.models.entity.Venta;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IVentaService;

@Service
public class VentaServiceImplement implements IVentaService {

	@Autowired
	private IVentaDao ventaDao;
	
	@Override
	public List<Venta> findAllByIdEmpresa(Long idEmpresa) {
		// TODO Auto-generated method stub
		return ventaDao.findAllByIdEmpresaQuery(idEmpresa);
	}

	@Override
	public List<Venta> findAllByIdPuntoVenta(Long idPunto) {
		// TODO Auto-generated method stub
		return ventaDao.findAllByIdPuntoVentaQuery(idPunto);
	}

}
