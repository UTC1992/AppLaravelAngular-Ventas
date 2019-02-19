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
@Table(name = "categoria_producto")
public class CategoriaProducto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCategoriaProducto;
	private String nombreCategoria;
	private String descripcionCategoria;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;

	@Column(name = "empresa_id")
	private Long idEmpresa;

	@OneToMany(fetch = FetchType.LAZY,mappedBy="categoria")
	private List<Producto> lsProducto;

	public CategoriaProducto() {
		this.prePersist();
		this.lsProducto = new ArrayList<>();
	}

	
	// constructor
	
	/// prepersistencia

	public void prePersist() {
		this.createdAt = new Date();
	}

	public Long getIdCategoriaProducto() {
		return idCategoriaProducto;
	}

	public void setIdCategoriaProducto(Long idCategoriaProducto) {
		this.idCategoriaProducto = idCategoriaProducto;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public String getDescripcionCategoria() {
		return descripcionCategoria;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public void setDescripcionCategoria(String descripcionCategoria) {
		this.descripcionCategoria = descripcionCategoria;
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

	public List<Producto> getLsProducto() {
		return lsProducto;
	}

	public void setLsProducto(List<Producto> lsProducto) {
		this.lsProducto = lsProducto;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
