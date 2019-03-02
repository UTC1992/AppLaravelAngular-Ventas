package com.jmc.backend.ventas.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jmc.backend.ventas.apirest.models.entity.Compra;

public interface IComprasDao extends JpaRepository<Compra, Long> {

	@Query("select c from Compra c where c.idEmpresa=?1")
	public List<Compra> findAllByEmpresaQuery(Long id);
}
