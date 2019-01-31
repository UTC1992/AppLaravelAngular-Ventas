package com.jmc.backend.ventas.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jmc.backend.ventas.apirest.models.entity.Configuracion;

public interface IConfiguracionDao extends JpaRepository<Configuracion, Long> {

	@Query("select c from Configuracion c where c.idEmpresa=?1")
	public List<Configuracion> findAllByIdEmpresaQuery(Long idEmpresa);
}
