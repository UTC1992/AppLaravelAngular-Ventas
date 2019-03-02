package com.jmc.backend.ventas.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	@JsonIgnoreProperties(value={"categoria","tipoProducto","hibernateLazyInitializer", "handler"} ,allowSetters=true)
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="producto_id")
	private Producto producto;
	
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

	
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	
	// metodos de acciones 
	public Double getImporte() {
		return cantidad.doubleValue() * producto.getPrecioVentaProducto();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
