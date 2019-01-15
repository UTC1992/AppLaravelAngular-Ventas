package com.jmc.backend.ventas.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.bytebuddy.dynamic.loading.ClassReloadingStrategy.Strategy;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idProducto;
	private String codigoProducto;
	private String nombreProducto;
	private String descripcionProducto;
	private Integer stockProducto;
	private Integer stockMinProducto;
	private Double precioCostoProducto;
	private Double precioVentaProducto;
	private Double utilidadProducto;
	private String estadoProducto;
	@Column(name="created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;
	private String Imagen;

	private CategoriaProducto categoriaProducto;

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public Integer getStockProducto() {
		return stockProducto;
	}

	public void setStockProducto(Integer stockProducto) {
		this.stockProducto = stockProducto;
	}

	public Integer getStockMinProducto() {
		return stockMinProducto;
	}

	public void setStockMinProducto(Integer stockMinProducto) {
		this.stockMinProducto = stockMinProducto;
	}

	public Double getPrecioCostoProducto() {
		return precioCostoProducto;
	}

	public void setPrecioCostoProducto(Double precioCostoProducto) {
		this.precioCostoProducto = precioCostoProducto;
	}

	public Double getPrecioVentaProducto() {
		return precioVentaProducto;
	}

	public void setPrecioVentaProducto(Double precioVentaProducto) {
		this.precioVentaProducto = precioVentaProducto;
	}

	public Double getUtilidadProducto() {
		return utilidadProducto;
	}

	public void setUtilidadProducto(Double utilidadProducto) {
		this.utilidadProducto = utilidadProducto;
	}

	public String getEstadoProducto() {
		return estadoProducto;
	}

	public void setEstadoProducto(String estadoProducto) {
		this.estadoProducto = estadoProducto;
	}

	public String getImagen() {
		return Imagen;
	}

	public void setImagen(String imagen) {
		Imagen = imagen;
	}

	public CategoriaProducto getCategoriaProducto() {
		return categoriaProducto;
	}

	public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
