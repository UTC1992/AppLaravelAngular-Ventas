package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmc.backend.ventas.apirest.models.dao.ITipoDocumentoDao;
import com.jmc.backend.ventas.apirest.models.entity.TipoDocumento;

@Service
public class TipoDocumentoServiceImplement implements ITipoDocumentoService{

	@Autowired
	private ITipoDocumentoDao tipoDocumentoDao;
	
	@Override
	public List<TipoDocumento> findAll(Long id) {
		// TODO Auto-generated method stub
		
		return tipoDocumentoDao.findByIdEmpresaQuery(id);
	}

	@Override
	public TipoDocumento findById(Long id) {
		// TODO Auto-generated method stub
		return tipoDocumentoDao.findById(id).orElse(null);
	}

	@Override
	public TipoDocumento save(TipoDocumento tipo) {
		// TODO Auto-generated method stub
		return tipoDocumentoDao.save(tipo);
	}

	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		tipoDocumentoDao.deleteById(id);
		return true;
	}

}
