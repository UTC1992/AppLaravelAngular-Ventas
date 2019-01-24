package com.jmc.backend.ventas.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jmc.backend.ventas.apirest.models.entity.TipoDocumento;

public interface ITipoDocumentoDao extends JpaRepository<TipoDocumento, Long>{

	
	@Query("select t from TipoDocumento t where t.idEmpresa=?1")
	public List<TipoDocumento> findByIdEmpresaQuery(Long id);
}
