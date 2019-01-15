package com.jmc.backend.ventas.apirest.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jmc.backend.ventas.apirest.models.entity.Cliente;

public interface IClienteService {
	
	public List<Cliente> findAll();
	public Page<Cliente> findAll(Pageable pageable);
	public Cliente findById(Long Id);
	public Cliente save(Cliente cliente);
	public void delete(Long id);

}
