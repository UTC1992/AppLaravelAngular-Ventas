package com.jmc.backend.ventas.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "bodegas_sucursal")
public class BodegaSucursal implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnoreProperties(value= {"lsVentas","lsCompras","lsProductos","hibernateLazyInitializer", "handler"},allowSetters=true)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "punto_venta_id")
	private PuntoVenta punto;

	private Integer cantidad;

	@JsonIgnoreProperties(value={"categoria","tipoProducto","hibernateLazyInitializer", "handler"},allowSetters=true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id")
	private Producto producto;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_At")
	private Date createdAt;

	@Temporal(TemporalType.DATE)
	@Column(name = "updated_at")
	private Date updatedAt;

	public BodegaSucursal() {
		super();
		// TODO Auto-generated constructor stub
	}

	@PrePersist
	private void prePerdist() {
		this.createdAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PuntoVenta getPunto() {
		return punto;
	}

	public void setPunto(PuntoVenta punto) {
		this.punto = punto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public void agregarStockBodega(Integer newCantidad) {
		this.cantidad=newCantidad;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
