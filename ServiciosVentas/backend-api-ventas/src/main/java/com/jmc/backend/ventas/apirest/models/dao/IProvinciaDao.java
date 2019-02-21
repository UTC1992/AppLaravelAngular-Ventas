package com.jmc.backend.ventas.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmc.backend.ventas.apirest.models.entity.statics.Provincia;

public interface IProvinciaDao extends JpaRepository<Provincia, Long> {

}
