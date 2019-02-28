package com.jmc.backend.ventas.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jmc.backend.ventas.apirest.models.entity.Provedor;

public interface IProvedorDao extends JpaRepository<Provedor, Long> {

	@Query("select p from Provedor p where p.idEmpresa=?1")
	public List<Provedor> findAllByIdEmpresaQuery(Long idEmpresa);
	
	@Query("select p from Provedor p where p.email=?1 and p.idEmpresa=?2")
	public Provedor findByEmailQuery(String email, Long idEmpresa);
	
	@Query("select p from Provedor p where p.ruc=?1 and p.idEmpresa=?2")
	public Provedor findByRucQuery(String ruc,Long idEmpresa);
	
	@Query("select p from Provedor p where p.cedula=?1 and p.idEmpresa=?2 ")
	public Provedor findByCedulaQuery(String cedula, Long idEmpresa);
}
