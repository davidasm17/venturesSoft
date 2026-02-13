package com.prueba.venturessoft.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.venturessoft.dto.EmpleadoDetalleDTO;
import com.prueba.venturessoft.model.HuCatMoneda;
import com.prueba.venturessoft.model.HuEmpls;
import com.prueba.venturessoft.model.entity.HuCatMonedaId;
import com.prueba.venturessoft.model.entity.HuEmplsId;
import com.prueba.venturessoft.service.EmpleadoService;
import com.prueba.venturessoft.security.JwtService;

@WebMvcTest(HuEmplsController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = {"basePath=/api/empleados"})
public class HuEmplsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpleadoService empleadoService;
    
    @MockBean
    private JwtService jwtService; 

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCrearEmpleado() throws Exception {
        HuEmplsId id = new HuEmplsId(1, 100);
        HuEmpls emp = new HuEmpls();
        emp.setId(id);
        emp.setNombre("Juan");
        emp.setApellidoPaterno("Perez");
        emp.setApellidoMaterno("Lopez");
        emp.setClaveMoneda("MXN");
        emp.setPuesto("Dev");

        when(empleadoService.crearEmpleado(any(HuEmpls.class))).thenReturn(emp);

        mockMvc.perform(post("/api/empleados")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.datos.nombre").value("Juan"));
    }
    
    @Test
    public void testActualizarEmpleado() throws Exception {
    	HuEmplsId id = new HuEmplsId(1, 100);
        HuEmpls emp = new HuEmpls();
        emp.setId(id);
        emp.setNombre("Juan Updated");
        emp.setApellidoPaterno("Perez");
        emp.setApellidoMaterno("Lopez");
        emp.setClaveMoneda("MXN");
        emp.setPuesto("Dev");
        
        when(empleadoService.actualizarEmpleado(eq(1), eq(100), any(HuEmpls.class))).thenReturn(emp);
        
        mockMvc.perform(put("/api/empleados/1/100")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emp)))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.nombre").value("Juan Updated"));
    }
    
    @Test
    public void testEliminarEmpleado() throws Exception {
    	mockMvc.perform(delete("/api/empleados/1/100"))
    			.andExpect(status().isNoContent());
    }
    
    @Test
    public void testObtenerDetalleEmpleado() throws Exception {
    	HuEmplsId empId = new HuEmplsId(1, 100);
    	HuEmpls emp = new HuEmpls();
    	emp.setId(empId);
    	emp.setNombre("Juan");
    	
    	HuCatMonedaId monedaId = new HuCatMonedaId(1, "MXN");
    	HuCatMoneda moneda = new HuCatMoneda(monedaId, "Peso", "$", "A");
    	
    	EmpleadoDetalleDTO dto = new EmpleadoDetalleDTO(emp, moneda);
    	
    	when(empleadoService.obtenerDetalleEmpleado(1, 100)).thenReturn(dto);
    	
    	mockMvc.perform(get("/api/empleados/1/100"))
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("$.empleado.nombre").value("Juan"))
    			.andExpect(jsonPath("$.moneda.descripcion").value("Peso"));
    }
}
