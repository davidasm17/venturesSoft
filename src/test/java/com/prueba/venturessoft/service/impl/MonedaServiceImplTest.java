package com.prueba.venturessoft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prueba.venturessoft.exception.RecursoNoEncontradoException;
import com.prueba.venturessoft.model.HuCatMoneda;
import com.prueba.venturessoft.model.entity.HuCatMonedaId;
import com.prueba.venturessoft.repository.MonedaRepository;

@ExtendWith(MockitoExtension.class)
public class MonedaServiceImplTest {

    @Mock
    private MonedaRepository monedaRepository;

    @InjectMocks
    private MonedaServiceImpl monedaService;

    @Test
    public void testCrearMoneda() {
        HuCatMonedaId id = new HuCatMonedaId(1, "MXN");
        HuCatMoneda moneda = new HuCatMoneda(id, "Peso", "$", "A");

        when(monedaRepository.save(any(HuCatMoneda.class))).thenReturn(moneda);

        HuCatMoneda created = monedaService.crearMoneda(moneda);

        assertNotNull(created);
        assertEquals("MXN", created.getId().getClaveMoneda());
        verify(monedaRepository, times(1)).save(moneda);
    }

    @Test
    public void testObtenerMonedaPorId_Exito() {
        HuCatMonedaId id = new HuCatMonedaId(1, "MXN");
        HuCatMoneda moneda = new HuCatMoneda(id, "Peso", "$", "A");

        when(monedaRepository.findById(id)).thenReturn(Optional.of(moneda));

        HuCatMoneda found = monedaService.obtenerMonedaPorId(1, "MXN");

        assertNotNull(found);
        assertEquals("Peso", found.getDescripcion());
    }

    @Test
    public void testObtenerMonedaPorId_NoEncontrado() {
        HuCatMonedaId id = new HuCatMonedaId(1, "MXN");
        when(monedaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> {
            monedaService.obtenerMonedaPorId(1, "MXN");
        });
    }

    @Test
    public void testActualizarMoneda_Exito() {
        HuCatMonedaId id = new HuCatMonedaId(1, "MXN");
        HuCatMoneda moneda = new HuCatMoneda(id, "Peso Nuevo", "$", "A");

        when(monedaRepository.existsById(id)).thenReturn(true);
        when(monedaRepository.save(moneda)).thenReturn(moneda);

        HuCatMoneda updated = monedaService.actualizarMoneda(1, "MXN", moneda);

        assertNotNull(updated);
        assertEquals("Peso Nuevo", updated.getDescripcion());
    }

    @Test
    public void testEliminarMoneda() {
        HuCatMonedaId id = new HuCatMonedaId(1, "MXN");
        
        monedaService.eliminarMoneda(1, "MXN");
        verify(monedaRepository, times(1)).deleteById(id);
    }
    
    @Test
    public void testListarMonedas() {
    	HuCatMonedaId id1 = new HuCatMonedaId(1, "MXN");
        HuCatMoneda moneda1 = new HuCatMoneda(id1, "Peso", "$", "A");
        
        HuCatMonedaId id2 = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda2 = new HuCatMoneda(id2, "Dolar", "$", "A");
        
        when(monedaRepository.findAll()).thenReturn(Arrays.asList(moneda1, moneda2));
        
        List<HuCatMoneda> lista = monedaService.listarMonedas();
        
        assertEquals(2, lista.size());
    }
}
