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
import com.jmc.backend.ventas.apirest.models.entity.statics.TipoPago;

@Entity
@Table(name = "ventas")
public class Venta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long IdVenta;

	private String serieVenta;
	private String numeroVenta;

	private Double totalVenta;
	private Double descuentoVenta;
	private Double subTotalVenta;
	private Double ivaVenta;
	private Double totalPagarVenta;
	private int estadoVenta;
	private String observacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_at")
	private Date createdAt;

	@Temporal(TemporalType.DATE)
	@Column(name = "updated_at")
	private Date updatedAt;

	// relaciones
	@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, allowSetters = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_documento_id")
	private TipoDocumento tipoDocumento;

	@JsonIgnoreProperties(value = {"hibernateLazyInitializer",
	"handler" })	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="tipo_pago_id")
	private TipoPago tipoPago;
	
	@Column(name = "punto_venta_id")
	private Long puntoVentaId;

	
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer",
	"handler" }, allowSetters = true)	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "venta_id")
	private List<DetalleVenta> detalleVenta;

	@JsonIgnoreProperties(value = { "lsVentas", "roles", "lsCompras", "hibernateLazyInitializer",
			"handler" }, allowSetters = true)
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

	@JsonIgnoreProperties(value = { "lsVentas", "hibernateLazyInitializer",
			"handler" }, allowSetters = true)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	
	private Long idEmpresa;

	// pre persistencia de datos
	@PrePersist
	public void prePersist() {
		createdAt = new Date();
	}

	// constructor
	public Venta() {
		this.detalleVenta = new ArrayList<>();
	}

	public TipoPago getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Long getPuntoVentaId() {
		return puntoVentaId;
	}

	public void setPuntoVentaId(Long puntoVentaId) {
		this.puntoVentaId = puntoVentaId;
	}

	// get and set
	public Long getIdVenta() {
		return IdVenta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setIdVenta(Long idVenta) {
		IdVenta = idVenta;
	}

	public String getSerieVenta() {
		return serieVenta;
	}

	public void setSerieVenta(String serieVenta) {
		this.serieVenta = serieVenta;
	}

	public String getNumeroVenta() {
		return numeroVenta;
	}

	public void setNumeroVenta(String numeroVenta) {
		this.numeroVenta = numeroVenta;
	}

	public Double getTotalVenta() {
		return totalVenta;
	}

	public void setTotalVenta(Double totalVenta) {
		this.totalVenta = totalVenta;
	}

	public Double getDescuentoVenta() {
		return descuentoVenta;
	}

	public void setDescuentoVenta(Double descuentoVenta) {
		this.descuentoVenta = descuentoVenta;
	}

	public Double getSubTotalVenta() {
		return subTotalVenta;
	}

	public void setSubTotalVenta(Double subTotalVenta) {
		this.subTotalVenta = subTotalVenta;
	}

	public Double getIvaVenta() {
		return ivaVenta;
	}

	public void setIvaVenta(Double ivaVenta) {
		this.ivaVenta = ivaVenta;
	}

	public Double getTotalPagarVenta() {
		return totalPagarVenta;
	}

	public void setTotalPagarVenta(Double totalPagarVenta) {
		this.totalPagarVenta = totalPagarVenta;
	}

	public int getEstadoVenta() {
		return estadoVenta;
	}

	public void setEstadoVenta(int estadoVenta) {
		this.estadoVenta = estadoVenta;
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public List<DetalleVenta> getDetalleVenta() {
		return detalleVenta;
	}

	public void setDetalleVenta(List<DetalleVenta> detalleVenta) {
		this.detalleVenta = detalleVenta;
	}

	public void addItemVenta(DetalleVenta detalle) {
		this.detalleVenta.add(detalle);
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	public Double getTotal() {
		Double total=0.00;
		for (DetalleVenta item : detalleVenta) {
			total+=item.getImporte();
		}
		return total;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
