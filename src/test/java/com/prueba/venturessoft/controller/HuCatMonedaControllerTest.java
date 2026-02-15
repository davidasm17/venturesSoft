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

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.venturessoft.model.HuCatMoneda;
import com.prueba.venturessoft.model.entity.HuCatMonedaId;
import com.prueba.venturessoft.security.JwtService;
import com.prueba.venturessoft.service.MonedaService;

@WebMvcTest(HuCatMonedaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HuCatMonedaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonedaService monedaService;
    
    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testListarMonedas() throws Exception {
    	HuCatMonedaId id = new HuCatMonedaId(1, "MXN");
    	HuCatMoneda moneda = new HuCatMoneda(id, "Peso", "$", "A");
    	
    	when(monedaService.listarMonedas()).thenReturn(Arrays.asList(moneda));
    	
    	mockMvc.perform(get("/api/monedas"))
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("$[0].descripcion").value("Peso"));
    }
    
    @Test
    public void testObtenerMoneda() throws Exception {
    	HuCatMonedaId id = new HuCatMonedaId(1, "MXN");
    	HuCatMoneda moneda = new HuCatMoneda(id, "Peso", "$", "A");
    	
    	when(monedaService.obtenerMonedaPorId(1, "MXN")).thenReturn(moneda);
    	
    	mockMvc.perform(get("/api/monedas/1/MXN"))
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("$.descripcion").value("Peso"));
    }
    
    @Test
    public void testCrearMoneda() throws Exception {
    	HuCatMonedaId id = new HuCatMonedaId(1, "MXN");
    	HuCatMoneda moneda = new HuCatMoneda(id, "Peso", "$", "A");
    	
    	when(monedaService.crearMoneda(any(HuCatMoneda.class))).thenReturn(moneda);
    	
    	mockMvc.perform(post("/api/monedas")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(moneda)))
    			.andExpect(status().isCreated())
    			.andExpect(jsonPath("$.descripcion").value("Peso"));
    }

    @Test
    public void testCrearMonedaDuplicada() throws Exception {
        HuCatMonedaId id = new HuCatMonedaId(1, "MXN");
        HuCatMoneda moneda = new HuCatMoneda(id, "Peso", "$", "A");
        
        when(monedaService.crearMoneda(any(HuCatMoneda.class)))
                .thenThrow(new com.prueba.venturessoft.exception.RecursoDuplicadoException("La moneda ya existe"));
        
        mockMvc.perform(post("/api/monedas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(moneda)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").value("La moneda ya existe"));
    }
    
    @Test
    public void testActualizarMoneda() throws Exception {
    	HuCatMonedaId id = new HuCatMonedaId(1, "MXN");
    	HuCatMoneda moneda = new HuCatMoneda(id, "Peso Nuevo", "$", "A");
    	
    	when(monedaService.actualizarMoneda(eq(1), eq("MXN"), any(HuCatMoneda.class))).thenReturn(moneda);
    	
    	mockMvc.perform(put("/api/monedas/1/MXN")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(moneda)))
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("$.descripcion").value("Peso Nuevo"));
    }
    
    @Test
    public void testEliminarMoneda() throws Exception {
    	mockMvc.perform(delete("/api/monedas/1/MXN"))
    			.andExpect(status().isNoContent());
    }
}
