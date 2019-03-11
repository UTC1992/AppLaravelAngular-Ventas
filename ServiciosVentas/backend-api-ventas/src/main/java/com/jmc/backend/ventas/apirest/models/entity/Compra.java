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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "compras")
public class Compra implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numeroCompra;
	private Double subTotal;
	private Double impuesto;
	private Double descuento;
	private Double total;
	private int Estado;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;

	@JsonIgnoreProperties(value = { "", "hibernateLazyInitializer", "handler" }, allowSetters = true)
	@ManyToOne(fetch = FetchType.LAZY)
	private Provedor provedor;

	@JsonIgnoreProperties(value = { "", "hibernateLazyInitializer", "handler" }, allowSetters = true)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_documento_id")
	private TipoDocumento tipoDocumento;

	@JsonIgnoreProperties(value = { "producto","hibernateLazyInitializer", "handler" }, allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "compra_id")
	private List<DetalleCompra> lsDetalleCompra;

	@Column(name = "empresa_id")
	private Long idEmpresa;

	public Compra() {
		this.lsDetalleCompra = new ArrayList<>();
	}

	@PrePersist
	private void prePersist() {
		this.createdAt = new Date();
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroCompra() {
		return numeroCompra;
	}

	public void setNumeroCompra(String numeroCompra) {
		this.numeroCompra = numeroCompra;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(Double impuesto) {
		this.impuesto = impuesto;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public int getEstado() {
		return Estado;
	}

	public void setEstado(int estado) {
		Estado = estado;
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

	public Provedor getProvedor() {
		return provedor;
	}

	public void setProvedor(Provedor provedor) {
		this.provedor = provedor;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<DetalleCompra> getLsDetalleCompra() {
		return lsDetalleCompra;
	}

	public void setLsDetalleCompra(List<DetalleCompra> lsDetalleCompra) {
		this.lsDetalleCompra = lsDetalleCompra;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
