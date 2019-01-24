package com.jmc.backend.ventas.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "empresas")
public class Empresa implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	private Boolean enabled;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "empresa_id")
	private List<Usuario> usuarios;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "empresa_modules", joinColumns = @JoinColumn(name = "empresa_id"), inverseJoinColumns = @JoinColumn(name = "module_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "empresa_id", "module_id" }) })
	private List<Modulos> lsModulos;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "empresa_id")
	private List<CategoriaProducto> lsCategoriasProductos;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "empresa_id")
	private List<Provedor> lsProvedores;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "empresa_id")
	private List<Producto> lsProductos;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "empresa_id")
	private List<PuntoVenta> lsPuntosVenta;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "empresa_id")
	private List<Configuracion> lsConfiguracion;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "empresa_id")
	private List<TipoDocumento> lsTipoDoc;

	// constructor inicializa las listas
	public Empresa() {
		this.usuarios = new ArrayList<>();
		this.lsCategoriasProductos = new ArrayList<>();
		this.lsModulos = new ArrayList<>();
		this.lsProductos = new ArrayList<>();
		this.lsProvedores = new ArrayList<>();
		this.lsPuntosVenta = new ArrayList<>();
		this.lsConfiguracion = new ArrayList<>();
		this.lsTipoDoc= new ArrayList<>();
	}

	// prepersistencia crea la fecha de ingreso
	public void prePersist() {
		this.createdAt = new Date();
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Modulos> getLsModulos() {
		return lsModulos;
	}

	public void setLsModulos(List<Modulos> lsModulos) {
		this.lsModulos = lsModulos;
	}

	public List<CategoriaProducto> getLsCategoriasProductos() {
		return lsCategoriasProductos;
	}

	public void setLsCategoriasProductos(List<CategoriaProducto> lsCategoriasProductos) {
		this.lsCategoriasProductos = lsCategoriasProductos;
	}

	public List<Provedor> getLsProvedores() {
		return lsProvedores;
	}

	public void setLsProvedores(List<Provedor> lsProvedores) {
		this.lsProvedores = lsProvedores;
	}

	public List<Producto> getLsProductos() {
		return lsProductos;
	}

	public void setLsProductos(List<Producto> lsProductos) {
		this.lsProductos = lsProductos;
	}

	public List<PuntoVenta> getLsPuntosVenta() {
		return lsPuntosVenta;
	}

	public void setLsPuntosVenta(List<PuntoVenta> lsPuntosVenta) {
		this.lsPuntosVenta = lsPuntosVenta;
	}

	public List<Configuracion> getLsConfiguracion() {
		return lsConfiguracion;
	}

	public void setLsConfiguracion(List<Configuracion> lsConfiguracion) {
		this.lsConfiguracion = lsConfiguracion;
	}

	public List<TipoDocumento> getLsTipoDoc() {
		return lsTipoDoc;
	}

	public void setLsTipoDoc(List<TipoDocumento> lsTipoDoc) {
		this.lsTipoDoc = lsTipoDoc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
