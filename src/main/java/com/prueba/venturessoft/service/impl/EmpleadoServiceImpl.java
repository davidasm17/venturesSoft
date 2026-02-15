package com.prueba.venturessoft.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prueba.venturessoft.dto.EmpleadoDetalleDTO;
import com.prueba.venturessoft.exception.RecursoDuplicadoException;
import com.prueba.venturessoft.exception.RecursoNoEncontradoException;
import com.prueba.venturessoft.model.HuCatMoneda;
import com.prueba.venturessoft.model.HuEmpls;
import com.prueba.venturessoft.model.entity.HuCatMonedaId;
import com.prueba.venturessoft.model.entity.HuEmplsId;
import com.prueba.venturessoft.repository.EmpleadoRepository;
import com.prueba.venturessoft.repository.MonedaRepository;
import com.prueba.venturessoft.service.EmpleadoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    
    private final MonedaRepository monedaRepository;

    @Override
    @Transactional
    public HuEmpls crearEmpleado(HuEmpls empleado) {
        if (empleadoRepository.existsById(empleado.getId())) {
            throw new RecursoDuplicadoException("El empleado con ID " + empleado.getId().getNumEmp() +
                    " y CIA " + empleado.getId().getNumCia() + " ya existe.");
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public Optional<HuEmpls> obtenerEmpleadoPorId(Integer numCia, Integer numEmp) {
        return empleadoRepository.findById(new HuEmplsId(numCia, numEmp));
    }

    @Override
    @Transactional
    public HuEmpls actualizarEmpleado(Integer numCia, Integer numEmp, HuEmpls empleado) {
        HuEmplsId id = new HuEmplsId(numCia, numEmp);
        if (!empleadoRepository.existsById(id)) {
        	throw new RecursoNoEncontradoException("Empleado no encontrado con CIA:" + numCia + "y EMP: "+ numEmp);
            
        }
        empleado.setId(id);
        return empleadoRepository.save(empleado);
    }

    @Override
    @Transactional
    public void eliminarEmpleado(Integer numCia, Integer numEmp) {
    	HuEmplsId id = new HuEmplsId(numCia, numEmp);
    	if(!empleadoRepository.existsById(id)) {
    		throw new RecursoNoEncontradoException("No se puede eliminar, empleado no encontrado");
    	}
    	empleadoRepository.deleteById(id);
    }

    @Override
    public EmpleadoDetalleDTO obtenerDetalleEmpleado(Integer numCia, Integer numEmp) {
        
        HuEmplsId id = new HuEmplsId(numCia, numEmp);
        Optional<HuEmpls> empleadoOpt = empleadoRepository.findById(id);
        
        if (empleadoOpt.isPresent()) {
            HuEmpls emp = empleadoOpt.get();
            String claveMoneda = emp.getClaveMoneda();
            
            Optional<HuCatMoneda> monedaOpt = monedaRepository.findById(new HuCatMonedaId(numCia, claveMoneda));
            
            HuCatMoneda moneda = monedaOpt.orElse(null);
            
            return new EmpleadoDetalleDTO(emp, moneda);
        }
        return null;
    }
    @Override
    public List<HuEmpls> listarEmpleadosPorMoneda(Integer numCia, String claveMoneda) {
        return empleadoRepository.findByIdNumCiaAndClaveMoneda(numCia, claveMoneda);
    }

	@Override
	public List<HuEmpls> listarEmpleados() {
		return empleadoRepository.findAll();
		
	}
	
	@Override
	public List<HuEmpls> buscarPorApellido(String apellido){
		return empleadoRepository.findByApellidoPaterno(apellido);
	}
    

}
