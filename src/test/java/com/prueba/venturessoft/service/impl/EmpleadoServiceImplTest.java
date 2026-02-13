package com.prueba.venturessoft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prueba.venturessoft.dto.EmpleadoDetalleDTO;
import com.prueba.venturessoft.exception.RecursoNoEncontradoException;
import com.prueba.venturessoft.model.HuCatMoneda;
import com.prueba.venturessoft.model.HuEmpls;
import com.prueba.venturessoft.model.entity.HuCatMonedaId;
import com.prueba.venturessoft.model.entity.HuEmplsId;
import com.prueba.venturessoft.repository.EmpleadoRepository;
import com.prueba.venturessoft.repository.MonedaRepository;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceImplTest {

    @Mock
    private EmpleadoRepository empleadoRepository;
    
    @Mock
    private MonedaRepository monedaRepository;

    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    @Test
    public void testCrearEmpleado() {
        HuEmplsId id = new HuEmplsId(1, 100);
        HuEmpls emp = new HuEmpls();
        emp.setId(id);
        emp.setNombre("Juan");

        when(empleadoRepository.save(any(HuEmpls.class))).thenReturn(emp);

        HuEmpls created = empleadoService.crearEmpleado(emp);

        assertNotNull(created);
        assertEquals("Juan", created.getNombre());
    }

    @Test
    public void testObtenerEmpleadoPorId() {
        HuEmplsId id = new HuEmplsId(1, 100);
        HuEmpls emp = new HuEmpls();
        emp.setId(id);

        when(empleadoRepository.findById(id)).thenReturn(Optional.of(emp));

        Optional<HuEmpls> found = empleadoService.obtenerEmpleadoPorId(1, 100);

        assertEquals(true, found.isPresent());
    }

    @Test
    public void testActualizarEmpleado_NoEncontrado() {
        HuEmplsId id = new HuEmplsId(1, 100);
        HuEmpls emp = new HuEmpls();
        emp.setId(id);

        when(empleadoRepository.existsById(id)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> {
            empleadoService.actualizarEmpleado(1, 100, emp);
        });
    }

    @Test
    public void testEliminarEmpleado_NoEncontrado() {
        HuEmplsId id = new HuEmplsId(1, 100);
        when(empleadoRepository.existsById(id)).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> {
            empleadoService.eliminarEmpleado(1, 100);
        });
    }
    
    @Test
    public void testObtenerDetalleEmpleado() {
    	HuEmplsId empId = new HuEmplsId(1, 100);
    	HuEmpls emp = new HuEmpls();
    	emp.setId(empId);
    	emp.setClaveMoneda("MXN");
    	emp.setNombre("Juan");
    	
    	HuCatMonedaId monedaId = new HuCatMonedaId(1, "MXN");
    	HuCatMoneda moneda = new HuCatMoneda(monedaId, "Peso", "$", "A");
    	
    	when(empleadoRepository.findById(empId)).thenReturn(Optional.of(emp));
    	when(monedaRepository.findById(monedaId)).thenReturn(Optional.of(moneda));
    	
    	EmpleadoDetalleDTO dto = empleadoService.obtenerDetalleEmpleado(1, 100);
    	
    	assertNotNull(dto);
    	assertEquals("Juan", dto.getEmpleado().getNombre());
    	assertEquals("Peso", dto.getMoneda().getDescripcion());
    }
}
