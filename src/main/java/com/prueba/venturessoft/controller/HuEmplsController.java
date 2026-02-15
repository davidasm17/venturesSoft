package com.prueba.venturessoft.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.venturessoft.dto.ApiResponse;
import com.prueba.venturessoft.dto.EmpleadoDetalleDTO;
import com.prueba.venturessoft.model.HuEmpls;
import com.prueba.venturessoft.service.EmpleadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${basePath}")
@Tag(name = "Empleados", description = "API para gestión de Empleados")
@RequiredArgsConstructor
public class HuEmplsController {

	private final EmpleadoService empleadoService;

    @PostMapping
    @Operation(summary = "Alta de empleado")
    public ResponseEntity<ApiResponse<HuEmpls>> crearEmpleado(@Valid @RequestBody HuEmpls empleado) {
    	HuEmpls empleadoGuardado = empleadoService.crearEmpleado(empleado);
    	ApiResponse<HuEmpls> respuesta = new ApiResponse<>(
                "El empleado se registro correctamente con ID: " + empleadoGuardado.getId().getNumEmp(),
                empleadoGuardado, 
                true 
            );
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
        
    @PutMapping("/{numCia}/{numEmp}")
    @Operation(summary = "Actualización de empleado")
    public ResponseEntity<HuEmpls> actualizarEmpleado(@Valid @PathVariable Integer numCia, @PathVariable Integer numEmp, @RequestBody HuEmpls empleado) {
        return ResponseEntity.ok(empleadoService.actualizarEmpleado(numCia, numEmp, empleado));
    }

    @DeleteMapping("/{numCia}/{numEmp}")
    @Operation(summary = "Eliminación de empleado")
    public ResponseEntity<ApiResponse<Void>> eliminarEmpleado(@PathVariable Integer numCia, @PathVariable Integer numEmp) {
        empleadoService.eliminarEmpleado(numCia, numEmp);
        return ResponseEntity.ok(new ApiResponse<>("Empleado eliminado correctamente", null, true));
    }
    
    @GetMapping
    @Operation(summary = "Listar todos los empleados")
    public ResponseEntity<List<HuEmpls>>listarEmpleados(){
    	return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    @GetMapping("/{numCia}/{numEmp}")
    @Operation(summary = "Consulta de empleado con información de moneda (numCia, numEmp)")
    public ResponseEntity<EmpleadoDetalleDTO> obtenerDetalleEmpleado(@PathVariable Integer numCia, @PathVariable Integer numEmp) {
        return ResponseEntity.ok(empleadoService.obtenerDetalleEmpleado(numCia, numEmp));
    }

    @GetMapping("/moneda/{numCia}/{claveMoneda}")
    @Operation(summary = "Listar empleados por moneda (numCia, claveMoneda)")
    public ResponseEntity<List<HuEmpls>> listarEmpleadosPorMoneda(@PathVariable Integer numCia, @PathVariable String claveMoneda) {
        return ResponseEntity.ok(empleadoService.listarEmpleadosPorMoneda(numCia, claveMoneda));
    }
    
    
    @GetMapping("/listar/apellido")
    @Operation(summary = "Listar empleados por apellido")
    public ResponseEntity<List<HuEmpls>>buscar(@RequestParam String apellido){
    	List<HuEmpls> resultado = empleadoService.buscarPorApellido(apellido);
    	
    	return  ResponseEntity.ok(resultado);
    }
}
