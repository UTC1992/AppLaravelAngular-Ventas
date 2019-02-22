package com.jmc.backend.ventas.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jmc.backend.ventas.apirest.models.entity.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long> {

	@Query("select c from Cliente c where c.email=?1 and c.idEmpresa=?2")
	public Cliente findByEmailQuery(String email, Long idEmpresa);
	
	@Query("select c from Cliente c where c.cedula=?1 and c.idEmpresa=?2")
	public Cliente findByCedulaQuery(String cedula,Long idEmpresa);
	
	@Query("select c from Cliente c where c.ruc=?1 and c.idEmpresa=?2")
	public Cliente findByRucQuery(String ruc,Long idEmpresa);
}
