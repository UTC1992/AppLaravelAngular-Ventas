package com.jmc.backend.ventas.apirest.models.entity;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "compras")
public class Compra implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numeroCompra;
	private Double subTotal;
	private Double impuesto;
	private Double total;
	private String Estado;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;

	/// relaciones
	private Long punto_venta_id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Provedor provedor;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_documento_id")
	private TipoDocumento tipoDocumento;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "compra_id")
	private List<DetalleCompra> lsDetaleCompra;

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

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
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

	public Long getPunto_venta_id() {
		return punto_venta_id;
	}

	public void setPunto_venta_id(Long punto_venta_id) {
		this.punto_venta_id = punto_venta_id;
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

	public List<DetalleCompra> getLsDetaleCompra() {
		return lsDetaleCompra;
	}

	public void setLsDetaleCompra(List<DetalleCompra> lsDetaleCompra) {
		this.lsDetaleCompra = lsDetaleCompra;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
