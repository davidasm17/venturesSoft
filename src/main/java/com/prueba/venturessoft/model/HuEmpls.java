package com.prueba.venturessoft.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "HU_EMPLS")
public class HuEmpls {
	
	@EmbeddedId
	private HuEmplsId id;
	
	@Column(name = "CLAVE_MONEDA")
	private String claveMoneda;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column (name = "APELLIDO_PATERNO")
	private String apellidoPaterno;
	
	@Column (name = "APELLIDO_MATERNO")
	private String apellidoMaterno;
	
	@Column(name = "PUESTO")
	private String puesto;
	
	
	
	
	

}
