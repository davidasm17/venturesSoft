package com.prueba.venturessoft.model;

import com.prueba.venturessoft.model.entity.HuEmplsId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "HU_EMPLS")
public class HuEmpls {
	
	@EmbeddedId
	@Valid
	@NotNull(message = "El ID compuesto (Cia/Emp) es obligatorio")
	private HuEmplsId id;
	
	@Column(name = "CLAVE_MONEDA")
	@NotBlank(message = "La moneda es obligatoria")
    @Size(min = 3, max = 3, message = "La clave de moneda debe ser de 3 letras (ej. MXN)")
	private String claveMoneda;
	
	@Column(name = "NOMBRE")
	@NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 30, message = "El nombre debe tener entre 2 y 30 caracteres")
	private String nombre;
	
	@Column (name = "APELLIDO_PATERNO")
    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(min = 2, max = 30, message = "El apellido paterno debe tener entre 2 y 30 caracteres")
	private String apellidoPaterno;
	
	@Column (name = "APELLIDO_MATERNO")
    @Size(min = 2, max = 30, message = "El apellido materno debe tener entre 2 y 30 caracteres")
	private String apellidoMaterno;
	
	@Column(name = "PUESTO")
	@NotBlank(message = "El puesto es obligatorio")
	private String puesto;
	
	
	
	
	

}
