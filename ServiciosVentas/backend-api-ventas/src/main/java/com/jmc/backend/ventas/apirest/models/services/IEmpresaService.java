package com.jmc.backend.ventas.apirest.models.services;

import com.jmc.backend.ventas.apirest.models.entity.Empresa;

public interface IEmpresaService {

	public Empresa findById(Long Id);
	public Empresa save(Empresa empresa);
}
