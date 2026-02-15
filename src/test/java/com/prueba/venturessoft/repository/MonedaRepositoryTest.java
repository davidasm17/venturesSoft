package com.prueba.venturessoft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.prueba.venturessoft.model.HuCatMoneda;
import com.prueba.venturessoft.model.entity.HuCatMonedaId;

@DataJpaTest
public class MonedaRepositoryTest {

    @Autowired
    private MonedaRepository monedaRepository;

    @Test
    public void testGuardarYBuscarMoneda() {
        HuCatMonedaId id = new HuCatMonedaId(1, "MXN");
        HuCatMoneda moneda = new HuCatMoneda(id, "Peso Mexicano", "$", "A");

        monedaRepository.save(moneda);

        Optional<HuCatMoneda> encontrada = monedaRepository.findById(id);
        assertTrue(encontrada.isPresent());
        assertEquals("Peso Mexicano", encontrada.get().getDescripcion());
    }

    @Test
    public void testEliminarMoneda() {
        HuCatMonedaId id = new HuCatMonedaId(1, "USD");
        HuCatMoneda moneda = new HuCatMoneda(id, "Dolar Americano", "$", "A");
        monedaRepository.save(moneda);

        monedaRepository.deleteById(id);

        Optional<HuCatMoneda> encontrada = monedaRepository.findById(id);
        assertFalse(encontrada.isPresent());
    }
}
