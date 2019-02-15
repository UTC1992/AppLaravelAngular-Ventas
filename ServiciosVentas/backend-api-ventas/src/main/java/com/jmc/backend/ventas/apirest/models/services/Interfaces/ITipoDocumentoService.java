package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.TipoDocumento;

public interface ITipoDocumentoService {

	public List<TipoDocumento> findAll(Long id);
	public TipoDocumento findById(Long id);
	public TipoDocumento save(TipoDocumento tipo);
	public Boolean delete(Long id);
	public TipoDocumento findByName(String nombre, Long id);
}
