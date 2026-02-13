package com.prueba.venturessoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.venturessoft.model.HuEmpls;
import com.prueba.venturessoft.model.entity.HuEmplsId;

public interface EmpleadoRepository extends JpaRepository<HuEmpls, HuEmplsId>{
	
	List<HuEmpls> findByIdNumCiaAndClaveMoneda(Integer numCia, String claveMoneda);

}
