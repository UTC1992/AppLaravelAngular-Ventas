package com.jmc.backend.ventas.apirest.models.entity.statics;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="provincias")
public class Provincia implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	@Column(name="created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;
	
	@OneToMany(fetch= FetchType.LAZY,cascade= CascadeType.ALL)
	@JoinColumn(name="provincia_id")
	private List<Ciudad> lsCiudades;
	
	
	
	public Provincia() {
		//this.lsCiudades= new ArrayList<>();
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
	public List<Ciudad> getLsCiudades() {
		return lsCiudades;
	}
	public void setLsCiudades(List<Ciudad> lsCiudades) {
		this.lsCiudades = lsCiudades;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
