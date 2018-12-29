package com.jmc.backend.ventas.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="detalle_venta")
public class DetalleVenta implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idDetalleVenta;
	private Integer cantidad;
	private Double costoDetalle;
	private Double totalDetalle;
	private Double ivaDetalle;

	public Long getIdDetalleVenta() {
		return idDetalleVenta;
	}

	public void setIdDetalleVenta(Long idDetalleVenta) {
		this.idDetalleVenta = idDetalleVenta;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getCostoDetalle() {
		return costoDetalle;
	}

	public void setCostoDetalle(Double costoDetalle) {
		this.costoDetalle = costoDetalle;
	}

	public Double getTotalDetalle() {
		return totalDetalle;
	}

	public void setTotalDetalle(Double totalDetalle) {
		this.totalDetalle = totalDetalle;
	}

	public Double getIvaDetalle() {
		return ivaDetalle;
	}

	public void setIvaDetalle(Double ivaDetalle) {
		this.ivaDetalle = ivaDetalle;
	}

	
	// metodos de acciones 
	public Long calcularImporte() {
		return cantidad.longValue();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
