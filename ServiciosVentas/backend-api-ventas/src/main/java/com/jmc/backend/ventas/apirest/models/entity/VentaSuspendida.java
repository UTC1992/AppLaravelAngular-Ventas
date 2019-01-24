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

@Entity
@Table(name="ventas_suspendidas")
public class VentaSuspendida  implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String  observaciones;
	
	@Column(name="created_at")
	@Temporal(TemporalType.DATE)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.DATE)
	private Date updatedAt;
	
	private Long empresa_id;
	
	private Long usuario_id;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
