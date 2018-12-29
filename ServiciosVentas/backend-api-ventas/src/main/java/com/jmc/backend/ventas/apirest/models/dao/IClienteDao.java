package com.jmc.backend.ventas.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.jmc.backend.ventas.apirest.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> {

}
