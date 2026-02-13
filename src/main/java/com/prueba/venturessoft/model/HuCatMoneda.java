package com.prueba.venturessoft.model;

import com.prueba.venturessoft.model.entity.HuCatMonedaId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HU_CAT_MONEDA")
public class HuCatMoneda {
	
	@EmbeddedId
	@Valid
	@NotNull(message = "El ID de la moneda es obligatorio")
	private HuCatMonedaId id;
	
	@Column(name = "DESCRIPCION")
	@NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 30, message = "La descripción no puede exceder 30 caracteres")
	private String descripcion;
	
	@Column(name = "SIMBOLO")
	@NotBlank(message = "El símbolo es obligatorio")
    @Size(max = 4, message = "El símbolo es muy largo (máx 4 caracteres)")
	private String simbolo;
	
	@Column(name = "ESTATUS")
	@NotBlank(message = "El estatus es obligatorio")
	private String estatus;

}
