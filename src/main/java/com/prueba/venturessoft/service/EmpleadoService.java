package com.prueba.venturessoft.service;

import java.util.List;
import java.util.Optional;

import com.prueba.venturessoft.dto.EmpleadoDetalleDTO;
import com.prueba.venturessoft.model.HuCatMoneda;
import com.prueba.venturessoft.model.HuEmpls;

public interface EmpleadoService {
	
	HuEmpls crearEmpleado(HuEmpls empleado);
	Optional<HuEmpls> obtenerEmpleadoPorId(Integer numCia, Integer numEmp);
	HuEmpls actualizarEmpleado(Integer numCia, Integer numEmp, HuEmpls empleado);
	void eliminarEmpleado(Integer numCia, Integer numEmp);
    List<HuEmpls> listarEmpleados();
	EmpleadoDetalleDTO obtenerDetalleEmpleado(Integer numCia, Integer numEmp);
	List<HuEmpls> listarEmpleadosPorMoneda(Integer numCia, String claveMoneda);
}
