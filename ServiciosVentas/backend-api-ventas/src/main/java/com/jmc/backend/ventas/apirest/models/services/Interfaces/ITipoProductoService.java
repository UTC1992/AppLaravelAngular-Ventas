package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.statics.TipoProducto;

public interface ITipoProductoService {

	public List<TipoProducto> findAll();
}
