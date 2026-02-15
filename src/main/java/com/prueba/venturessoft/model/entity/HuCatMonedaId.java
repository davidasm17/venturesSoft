package com.prueba.venturessoft.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class HuCatMonedaId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "NUM_CIA")
	@NotNull(message = "El número de compañía es obligatorio")
    @Positive(message = "El número de compañía debe ser mayor a 0")
	private Integer numCia;
	
	@Column(name = "CLAVE_MONEDA")
	@NotBlank(message = "La clave de moneda es obligatoria")
    @Size(min = 3, max = 3, message = "La clave debe ser de 3 letras (Ej: MXN)")
    @Pattern(regexp = "^[A-Z]{3}$", message = "La clave debe ser mayúscula y solo letras")
	private String claveMoneda;

}
