package com.prueba.venturessoft.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class HuEmplsId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "NUM_CIA")
	@NotNull(message = "El número de compañía es obligatorio")
    @Positive(message = "El número de compañía debe ser mayor a 0")
	private Integer numCia;
	
	@Column(name = "NUM_EMP")
	@NotNull(message = "El número de empleado es obligatorio")
    @Positive(message = "El número de empleado debe ser mayor a 0")
	private Integer numEmp;

}
