package com.tingeso.eval1;

import com.tingeso.eval1.entities.PagosEntity;
import com.tingeso.eval1.entities.SubirDataEntity;
import com.tingeso.eval1.repositories.SubirDataRepository;
import com.tingeso.eval1.services.PagosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PagosServiceTest {

    @Autowired
    PagosService pagosService;

    @Autowired
    SubirDataRepository subirDataRepository;

    @Test
    void testTotalLeche1() {
        SubirDataEntity data1 = new SubirDataEntity();
        data1.setFecha("2023/03/17");
        data1.setQuincena("2023/03/2");
        data1.setTurno("M");
        data1.setProveedor("01003");
        data1.setKls_leche("50");
        subirDataRepository.save(data1);
        SubirDataEntity data2 = new SubirDataEntity();
        data2.setFecha("2023/03/17");
        data2.setQuincena("2023/03/2");
        data2.setTurno("T");
        data2.setProveedor("01003");
        data2.setKls_leche("45");
        subirDataRepository.save(data2);
        int result = Integer.parseInt(pagosService.totalLeche("01003","2023/03/2"));
        assertEquals(95, result, 0.0);
        subirDataRepository.deleteAll();
    }

    @Test
    void testDiasEntrega1() {
        SubirDataEntity data1 = new SubirDataEntity();
        data1.setFecha("2023/03/17");
        data1.setQuincena("2023/03/2");
        data1.setTurno("M");
        data1.setProveedor("01003");
        data1.setKls_leche("50");
        subirDataRepository.save(data1);
        SubirDataEntity data2 = new SubirDataEntity();
        data2.setFecha("2023/03/17");
        data2.setQuincena("2023/03/2");
        data2.setTurno("T");
        data2.setProveedor("01003");
        data2.setKls_leche("45");
        subirDataRepository.save(data2);
        int result = Integer.parseInt(pagosService.diasEntrega("01003","2023/03/2"));
        assertEquals(2, result, 0.0);
        subirDataRepository.deleteAll();
    }

    @Test
    void testPromedioLeche1() {
        SubirDataEntity data1 = new SubirDataEntity();
        data1.setFecha("2023/03/17");
        data1.setQuincena("2023/03/2");
        data1.setTurno("M");
        data1.setProveedor("01003");
        data1.setKls_leche("50");
        subirDataRepository.save(data1);
        SubirDataEntity data2 = new SubirDataEntity();
        data2.setFecha("2023/03/17");
        data2.setQuincena("2023/03/2");
        data2.setTurno("T");
        data2.setProveedor("01003");
        data2.setKls_leche("45");
        subirDataRepository.save(data2);
        int result = Integer.parseInt(pagosService.promedioLeche("01003","2023/03/2"));
        assertEquals(47, result, 0.0);
        subirDataRepository.deleteAll();
    }

    @Test
    void testVarLeche1() {
        int result = Integer.parseInt(pagosService.varLeche("01003","2023/03/2","95"));
        assertEquals(0, result, 0.0);
        subirDataRepository.deleteAll();
    }

    @Test
    void testVarLeche2() {
        PagosEntity pago1 = new PagosEntity();
        pago1.setQuincena("2023/03/1");
        pago1.setCodigo("01003");
        pago1.setKlsLeche("120");
        pagosService.guardarPago(pago1);
        int result = Integer.parseInt(pagosService.varLeche("01003","2023/03/2","95"));
        assertEquals(20, result, 0.0);
        pagosService.borrarTodos();
    }
}
