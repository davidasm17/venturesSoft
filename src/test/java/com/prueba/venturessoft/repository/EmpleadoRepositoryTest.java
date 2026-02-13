package com.prueba.venturessoft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.prueba.venturessoft.model.HuEmpls;
import com.prueba.venturessoft.model.entity.HuEmplsId;

@DataJpaTest
public class EmpleadoRepositoryTest {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Test
    public void testFindByIdNumCiaAndClaveMoneda() {
    	empleadoRepository.deleteAll();
    	
        // Arrange
        HuEmplsId id1 = new HuEmplsId(1, 100);
        HuEmpls emp1 = new HuEmpls();
        emp1.setId(id1);
        emp1.setClaveMoneda("MXN");
        emp1.setNombre("Juan");
        emp1.setApellidoPaterno("Perez");
        emp1.setApellidoMaterno("Lopez");
        emp1.setPuesto("Desarrollador");
        empleadoRepository.save(emp1);

        HuEmplsId id2 = new HuEmplsId(1, 101);
        HuEmpls emp2 = new HuEmpls();
        emp2.setId(id2);
        emp2.setClaveMoneda("USD");
        emp2.setNombre("Maria");
        emp2.setApellidoPaterno("Smith");
        emp2.setApellidoMaterno("Johnson");
        emp2.setPuesto("Gerente");
        empleadoRepository.save(emp2);
        
        HuEmplsId id3 = new HuEmplsId(2, 102);
        HuEmpls emp3 = new HuEmpls();
        emp3.setId(id3);
        emp3.setClaveMoneda("MXN");
        emp3.setNombre("Pedro");
        emp3.setApellidoPaterno("Gomez");
        emp3.setApellidoMaterno("Ruiz");
        emp3.setPuesto("Analista");
        empleadoRepository.save(emp3);

        // Act
        List<HuEmpls> resultados = empleadoRepository.findByIdNumCiaAndClaveMoneda(1, "MXN");

        // Assert
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("Juan", resultados.get(0).getNombre());
    }
}
