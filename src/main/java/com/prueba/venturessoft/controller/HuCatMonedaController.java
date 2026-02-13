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
import org.springframework.web.bind.annotation.RestController;

import com.prueba.venturessoft.dto.ApiResponse;
import com.prueba.venturessoft.model.HuCatMoneda;
import com.prueba.venturessoft.service.MonedaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/monedas")
@Tag(name = "Monedas", description = "API para gestión de Catálogo de Monedas")
@RequiredArgsConstructor
public class HuCatMonedaController {

	private final MonedaService monedaService;

    @GetMapping
    @Operation(summary = "Listar todas las monedas")
    public ResponseEntity<List<HuCatMoneda>> listarMonedas() {
        return ResponseEntity.ok(monedaService.listarMonedas());
    }

    @GetMapping("/{numCia}/{claveMoneda}")
    @Operation(summary = "Obtener moneda por ID compuesto")
    public ResponseEntity<HuCatMoneda> obtenerMoneda(@PathVariable Integer numCia, @PathVariable String claveMoneda) {
        HuCatMoneda moneda = monedaService.obtenerMonedaPorId(numCia, claveMoneda);
        return ResponseEntity.ok(moneda);
    }

    @PostMapping
    @Operation(summary = "Crear nueva moneda")
    public ResponseEntity<HuCatMoneda> crearMoneda(@Valid @RequestBody HuCatMoneda moneda) {
        return new ResponseEntity<>(monedaService.crearMoneda(moneda), HttpStatus.CREATED);
    }

    @PutMapping("/{numCia}/{claveMoneda}")
    @Operation(summary = "Actualizar moneda existente")
    public ResponseEntity<HuCatMoneda> actualizarMoneda(@Valid @PathVariable Integer numCia, @PathVariable String claveMoneda, @RequestBody HuCatMoneda moneda) {
        HuCatMoneda actualizado = monedaService.actualizarMoneda(numCia, claveMoneda, moneda);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{numCia}/{claveMoneda}")
    @Operation(summary = "Eliminar moneda")
    public ResponseEntity<ApiResponse<Void>> eliminarMoneda(@PathVariable Integer numCia, @PathVariable String claveMoneda) {
        monedaService.eliminarMoneda(numCia, claveMoneda);
        return ResponseEntity.ok(new ApiResponse<>("Moneda eliminada correctamente", null, true));
    }
}
