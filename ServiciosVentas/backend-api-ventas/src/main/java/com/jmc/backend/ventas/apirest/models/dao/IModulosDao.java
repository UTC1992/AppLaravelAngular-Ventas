package com.jmc.backend.ventas.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.jmc.backend.ventas.apirest.models.entity.Modulos;

public interface IModulosDao  extends CrudRepository<Modulos, Long>{

	
}
