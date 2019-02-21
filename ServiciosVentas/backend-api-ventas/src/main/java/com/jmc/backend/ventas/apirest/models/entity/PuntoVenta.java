package com.jmc.backend.ventas.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "punto_venta")
public class PuntoVenta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String descripcion;
	private String direccion;
	private String provincia;
	private String ciudad;
	private String telefono;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "punto_venta_id")
	private List<Venta> lsVentas;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "punto_venta_id")
	private List<Compra> lsCompras;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "punto_venta_id")
	private List<Producto> lsProductos;

	@Column(name = "empresa_id")
	private Long idEmpresa;

	
	public PuntoVenta() {
		this.lsProductos= new ArrayList<>();
		this.lsCompras= new ArrayList<>();
		prePersist();
	}
	private void prePersist() {
		this.createdAt= new Date();
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

	public List<Producto> getLsProductos() {
		return lsProductos;
	}

	public void setLsProductos(List<Producto> lsProductos) {
		this.lsProductos = lsProductos;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public List<Venta> getLsVentas() {
		return lsVentas;
	}

	public void setLsVentas(List<Venta> lsVentas) {
		this.lsVentas = lsVentas;
	}

	public List<Compra> getLsCompras() {
		return lsCompras;
	}

	public void setLsCompras(List<Compra> lsCompras) {
		this.lsCompras = lsCompras;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
