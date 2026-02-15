package com.prueba.venturessoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.venturessoft.model.HuCatMoneda;
import com.prueba.venturessoft.model.entity.HuCatMonedaId;

@Repository
public interface MonedaRepository extends JpaRepository<HuCatMoneda, HuCatMonedaId> {

}
