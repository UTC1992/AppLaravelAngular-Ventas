package com.jmc.backend.ventas.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmc.backend.ventas.apirest.models.entity.statics.TipoProducto;

public interface ITipoProductoDao extends JpaRepository<TipoProducto, Long>{

}
