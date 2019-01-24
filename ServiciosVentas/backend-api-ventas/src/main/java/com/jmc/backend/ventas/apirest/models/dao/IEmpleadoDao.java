package com.jmc.backend.ventas.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jmc.backend.ventas.apirest.models.entity.Usuario;

public interface IEmpleadoDao extends JpaRepository<Usuario, Long> {

	
	//public Boolean assignRoleUserQuery();
}
