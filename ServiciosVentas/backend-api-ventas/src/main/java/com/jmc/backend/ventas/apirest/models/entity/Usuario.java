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
@Table(name = "users")
public class Usuario implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "empresa_id")
	private Long idEmpresa;

	@Column(length = 35, unique = true)
	private String username;

	@Column(length = 60)
	private String password;

	private Boolean enabled;

	private String nombre;

	private String apellido;

	private String dni;
	private String direccion;
	private String telefono;
	private String celular;
	private Boolean discapacidad;
	private String carnet;
	@Column(name = "fecha_nacimiento")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	private Integer edad;
	private String genero;

	@Column(unique = true)
	private String email;

	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;

	/// RELACIONES
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL,CascadeType.MERGE})
	@JoinTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "user_id", "role_id" }) })
	private List<Role> roles;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<Venta> lsVentas;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	private List<Compra> lsCompras;

	// END RELACIONES

	
	public Usuario() {
		this.roles= new ArrayList<>();
		this.lsVentas= new ArrayList<>();
		this.lsCompras= new ArrayList<>();
		prePersist();
	}
	
	
	private void prePersist() {
		this.createdAt= new Date();
	}
	
	public Long getId() {
		return id;
	}


	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Boolean getDiscapacidad() {
		return discapacidad;
	}

	public void setDiscapacidad(Boolean discapacidad) {
		this.discapacidad = discapacidad;
	}

	public String getCarnet() {
		return carnet;
	}

	public void setCarnet(String carnet) {
		this.carnet = carnet;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
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
