package com.jmc.backend.ventas.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmc.backend.ventas.apirest.models.entity.BodegaSucursal;

public interface IBodegaSucursalDao extends JpaRepository<BodegaSucursal, Long> {

	
}
