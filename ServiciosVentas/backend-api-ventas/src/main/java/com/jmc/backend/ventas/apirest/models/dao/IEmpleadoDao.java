package com.jmc.backend.ventas.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jmc.backend.ventas.apirest.models.entity.Usuario;

public interface IEmpleadoDao extends JpaRepository<Usuario, Long> {

	
	@Query("select u from Usuario u where u.username=?1 and u.idEmpresa=?2")
	public Usuario findByUsernameQuery(String username,Long idEmpresa);
	
	@Query("select u from Usuario u where u.email=?1")
	public Usuario findEmailQuery(String email);
}
