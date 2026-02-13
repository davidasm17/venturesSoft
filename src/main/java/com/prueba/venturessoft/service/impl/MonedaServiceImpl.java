package com.prueba.venturessoft.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prueba.venturessoft.exception.RecursoNoEncontradoException;
import com.prueba.venturessoft.model.HuCatMoneda;
import com.prueba.venturessoft.model.entity.HuCatMonedaId;
import com.prueba.venturessoft.repository.MonedaRepository;
import com.prueba.venturessoft.service.MonedaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonedaServiceImpl implements MonedaService {
	
	

    private final MonedaRepository monedaRepository;

    @Override
    public HuCatMoneda crearMoneda(HuCatMoneda moneda) {
        return monedaRepository.save(moneda);
    }

    @Override
    public HuCatMoneda obtenerMonedaPorId(Integer numCia, String claveMoneda) {
    	HuCatMonedaId id = new HuCatMonedaId(numCia, claveMoneda);
        return monedaRepository.findById(id)
        		.orElseThrow(() -> new RecursoNoEncontradoException(
        				"Moneda no encontrada con CIA: " + numCia + " y Clave: " + claveMoneda));
    }

    @Override
    public HuCatMoneda actualizarMoneda(Integer numCia, String claveMoneda, HuCatMoneda moneda) {
        HuCatMonedaId id = new HuCatMonedaId(numCia, claveMoneda);
        if (monedaRepository.existsById(id)) {
            moneda.setId(id); 
            return monedaRepository.save(moneda);
        }
        return null; 
    }

    @Override
    public void eliminarMoneda(Integer numCia, String claveMoneda) {
        monedaRepository.deleteById(new HuCatMonedaId(numCia, claveMoneda));
    }

    @Override
    public List<HuCatMoneda> listarMonedas() {
        return monedaRepository.findAll();
    }
}
