package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.statics.Provincia;

public interface IProvinciaService {

	
	public List<Provincia> findAll();
	public Provincia findById(Long id);
	public Provincia save(Provincia provincia);
	public Boolean delete(Long id);
}
