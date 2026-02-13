package com.prueba.venturessoft.dto;

import com.prueba.venturessoft.model.HuCatMoneda;
import com.prueba.venturessoft.model.HuEmpls;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmpleadoDetalleDTO {
	
	private HuEmpls empleado;
	private HuCatMoneda moneda;

}
