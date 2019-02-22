package com.jmc.backend.ventas.apirest.models.services.Interfaces;

import java.util.List;

import com.jmc.backend.ventas.apirest.models.entity.Provedor;

public interface IProvedorService {

	public List<Provedor> findAll(Long idEmpresa);
	public Provedor save(Provedor provedor);
	public Provedor findById(Long id);
	public Provedor findByRuc(String ruc, Long idEmpresa);
	public Provedor findByCedula(String cedula, Long idEmpresa);
	public Provedor findByEmail(String email, Long idEmpresa);
	public void delete(Long id);
}
