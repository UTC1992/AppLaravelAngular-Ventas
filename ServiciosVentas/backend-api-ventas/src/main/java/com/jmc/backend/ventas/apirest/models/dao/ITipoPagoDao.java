package com.jmc.backend.ventas.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmc.backend.ventas.apirest.models.entity.statics.TipoPago;

public interface ITipoPagoDao extends JpaRepository<TipoPago, Long> {

}
