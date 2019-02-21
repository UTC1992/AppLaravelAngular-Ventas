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

import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jmc.backend.ventas.apirest.models.entity.statics.TipoProducto;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProducto;
	private String codigoProducto;
	private String nombreProducto;
	private String descripcionProducto;
	private Integer stockProducto;
	private Integer stockMinProducto;
	private Double precioCostoProducto;
	private Double precioVentaProducto;
	private Double utilidadProducto;
	private Boolean estadoProducto;
	private String observaciones;

	// @Lob
	// private byte[] imagen;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	@Column(name = "empresa_id")
	private Long idEmpresa;


	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;
	//private String Imagen;

	
	@JsonIgnoreProperties(value={"lsProductos", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="categoria_id")
	private CategoriaProducto categoria;
	
	
	@JsonIgnoreProperties(value={"lsProductos", "hibernateLazyInitializer", "handler"}, allowSetters=true)
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="tipo_producto_id")
	private TipoProducto tipoProducto;
	
	


	public Producto() {
		//prePersist();
	}

	@PrePersist
	public void prePersist() {
		this.createdAt = new Date();
	}

	

	public CategoriaProducto getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaProducto categoria) {
		this.categoria = categoria;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

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

	public Boolean getEstadoProducto() {
		return estadoProducto;
	}

	public void setEstadoProducto(Boolean estadoProducto) {
		this.estadoProducto = estadoProducto;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	//calcula ganancia venta de producto
	public Double calcularUtilidad() {
		return (this.precioVentaProducto)-(this.precioCostoProducto);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
