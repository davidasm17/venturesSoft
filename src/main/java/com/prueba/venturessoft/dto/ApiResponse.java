package com.prueba.venturessoft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
	private String mensaje;
    private T datos;
    private boolean exito;
}
