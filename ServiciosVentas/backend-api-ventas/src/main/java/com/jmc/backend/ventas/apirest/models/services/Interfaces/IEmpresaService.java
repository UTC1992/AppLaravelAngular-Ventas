package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import com.jmc.backend.ventas.apirest.models.entity.Empresa;

public interface IEmpresaService {

	public Empresa findById(Long Id);
	public Empresa save(Empresa empresa);
}
