package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmc.backend.ventas.apirest.models.dao.IEmpresaDao;
import com.jmc.backend.ventas.apirest.models.dao.IPuntoVentaDao;
import com.jmc.backend.ventas.apirest.models.entity.Empresa;
import com.jmc.backend.ventas.apirest.models.entity.PuntoVenta;
import com.jmc.backend.ventas.apirest.models.services.Interfaces.IPuntoVentaService;

@Service
public class PuntoVentaServiceImplement implements IPuntoVentaService {

	@Autowired
	private IPuntoVentaDao puntoDao;
	@Autowired
	private IEmpresaDao empresaDao;

	@Override
	@Transactional(readOnly=true)
	public List<PuntoVenta> findAll(Long idEmpresa) {
		// TODO Auto-generated method stub
		return puntoDao.findAllByIdEmpresaQuery(idEmpresa);
	}

	@Override
	@Transactional(readOnly=true)
	public PuntoVenta findById(Long id) {
		// TODO Auto-generated method stub
		return puntoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public PuntoVenta save(PuntoVenta puntoVenta) {
		// TODO Auto-generated method stub
		return puntoDao.save(puntoVenta);
	}

	@Override
	@Transactional
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		 puntoDao.deleteById(id);
		 return true;
	}

}
