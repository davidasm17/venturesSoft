package com.prueba.venturessoft.service;

import java.util.List;

import com.prueba.venturessoft.model.HuCatMoneda;

public interface MonedaService {
    
    HuCatMoneda crearMoneda(HuCatMoneda moneda);
    HuCatMoneda obtenerMonedaPorId(Integer numCia, String claveMoneda);
    HuCatMoneda actualizarMoneda(Integer numCia, String claveMoneda, HuCatMoneda moneda);
    void eliminarMoneda(Integer numCia, String claveMoneda);
    List<HuCatMoneda> listarMonedas();
}
