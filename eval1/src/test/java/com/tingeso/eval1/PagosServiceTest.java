package com.tingeso.eval1;

import com.tingeso.eval1.entities.PagosEntity;
import com.tingeso.eval1.entities.ProveedorEntity;
import com.tingeso.eval1.entities.SubirDataEntity;
import com.tingeso.eval1.entities.SubirDetailsEntity;
import com.tingeso.eval1.repositories.SubirDataRepository;
import com.tingeso.eval1.services.PagosService;
import com.tingeso.eval1.services.ProveedorService;
import com.tingeso.eval1.services.SubirDataService;
import com.tingeso.eval1.services.SubirDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PagosServiceTest {

    @Autowired
    PagosService pagosService;

    @Autowired
    SubirDataService subirDataService;

    @Autowired
    SubirDetailsService subirDetailsService;

    @Autowired
    ProveedorService proveedorService;

    @Autowired
    SubirDataRepository subirDataRepository;

    @Test
    void testTotalLeche1() {
        subirDataService.eliminarData();
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
        pagosService.borrarTodos();
        int result = Integer.parseInt(pagosService.varLeche("01003","2023/03/2","95"));
        assertEquals(0, result, 0.0);
        subirDataRepository.deleteAll();
    }

    @Test
    void testVarLeche2() {
        pagosService.borrarTodos();
        PagosEntity pago1 = new PagosEntity();
        pago1.setQuincena("2023/03/1");
        pago1.setCodigo("01003");
        pago1.setKlsLeche("120");
        pagosService.guardarPago(pago1);
        int result = Integer.parseInt(pagosService.varLeche("01003","2023/03/2","95"));
        assertEquals(20, result, 0.0);
        pagosService.borrarTodos();
    }

    @Test
    void testVarGrasa1() {
        pagosService.borrarTodos();
        int result = Integer.parseInt(pagosService.varGrasa("01003","2023/03/2","95"));
        assertEquals(0, result, 0.0);
        subirDataRepository.deleteAll();
    }

    @Test
    void testVarGrasa2() {
        pagosService.borrarTodos();
        PagosEntity pago1 = new PagosEntity();
        pago1.setQuincena("2023/03/1");
        pago1.setCodigo("01003");
        pago1.setGrasa("150");
        pagosService.guardarPago(pago1);
        int result = Integer.parseInt(pagosService.varGrasa("01003","2023/03/2","75"));
        assertEquals(50, result, 0.0);
        pagosService.borrarTodos();
    }

    @Test
    void testVarSolidos1() {
        pagosService.borrarTodos();
        int result = Integer.parseInt(pagosService.varSolidos("01003","2023/03/2","95"));
        assertEquals(0, result, 0.0);
        subirDataRepository.deleteAll();
    }

    @Test
    void testVarSolidos2() {
        pagosService.borrarTodos();
        PagosEntity pago1 = new PagosEntity();
        pago1.setQuincena("2023/03/1");
        pago1.setCodigo("01003");
        pago1.setSolidos("183");
        pagosService.guardarPago(pago1);
        int result = Integer.parseInt(pagosService.varSolidos("01003","2023/03/2","97"));
        assertEquals(46, result, 0.0);
        pagosService.borrarTodos();
    }

    @Test
    void testPagoXLeche1() {
        int result = Integer.parseInt(pagosService.pagoXLeche("A","163"));
        assertEquals(114100, result, 0.0);
    }

    @Test
    void testPagoXLeche2() {
        int result = Integer.parseInt(pagosService.pagoXLeche("B","99"));
        assertEquals(54450, result, 0.0);
    }

    @Test
    void testPagoXLeche3() {
        int result = Integer.parseInt(pagosService.pagoXLeche("C","235"));
        assertEquals(94000, result, 0.0);
    }

    @Test
    void testPagoXLeche4() {
        int result = Integer.parseInt(pagosService.pagoXLeche("D","327"));
        assertEquals(81750, result, 0.0);
    }

    @Test
    void testPagoXGrasa1() {
        int result = Integer.parseInt(pagosService.pagoXGrasa("15","171"));
        assertEquals(5130, result, 0.0);
    }

    @Test
    void testPagoXGrasa2() {
        int result = Integer.parseInt(pagosService.pagoXGrasa("45","244"));
        assertEquals(19520, result, 0.0);
    }

    @Test
    void testPagoXGrasa3() {
        int result = Integer.parseInt(pagosService.pagoXGrasa("46","355"));
        assertEquals(42600, result, 0.0);
    }

    @Test
    void testPagoXST1() {
        int result = Integer.parseInt(pagosService.pagoXST("7","159"));
        assertEquals(-20670, result, 0.0);
    }

    @Test
    void testPagoXST2() {
        int result = Integer.parseInt(pagosService.pagoXST("15","206"));
        assertEquals(-18540, result, 0.0);
    }

    @Test
    void testPagoXST3() {
        int result = Integer.parseInt(pagosService.pagoXST("21","273"));
        assertEquals(25935, result, 0.0);
    }

    @Test
    void testPagoXST4() {
        int result = Integer.parseInt(pagosService.pagoXST("40","305"));
        assertEquals(45750, result, 0.0);
    }

    @Test
    void testCalculoBonificacion1() {
        subirDataService.eliminarData();
        SubirDataEntity data1 = new SubirDataEntity();
        data1.setFecha("2023/03/17");
        data1.setQuincena("2023/03/2");
        data1.setTurno("M");
        data1.setProveedor("01003");
        data1.setKls_leche("50");
        subirDataRepository.save(data1);
        int result = Integer.parseInt(pagosService.calculoBonificacion("01003","2023/03/2"));
        assertEquals(0, result, 0.0);
        subirDataService.eliminarData();
    }

    @Test
    void testCalculoBonificacion2() {
        subirDataService.eliminarData();
        SubirDataEntity data1 = new SubirDataEntity();
        data1.setFecha("2023/03/17");
        data1.setQuincena("2023/03/2");
        data1.setTurno("M");
        data1.setProveedor("01003");
        data1.setKls_leche("50");
        subirDataRepository.save(data1);
        SubirDataEntity data2 = new SubirDataEntity();
        data2.setFecha("2023/03/18");
        data2.setQuincena("2023/03/2");
        data2.setTurno("M");
        data2.setProveedor("01003");
        data2.setKls_leche("51");
        subirDataRepository.save(data2);
        SubirDataEntity data3 = new SubirDataEntity();
        data3.setFecha("2023/03/19");
        data3.setQuincena("2023/03/2");
        data3.setTurno("M");
        data3.setProveedor("01003");
        data3.setKls_leche("52");
        subirDataRepository.save(data3);
        SubirDataEntity data4 = new SubirDataEntity();
        data4.setFecha("2023/03/20");
        data4.setQuincena("2023/03/2");
        data4.setTurno("M");
        data4.setProveedor("01003");
        data4.setKls_leche("53");
        subirDataRepository.save(data4);
        SubirDataEntity data5 = new SubirDataEntity();
        data5.setFecha("2023/03/21");
        data5.setQuincena("2023/03/2");
        data5.setTurno("M");
        data5.setProveedor("01003");
        data5.setKls_leche("54");
        subirDataRepository.save(data5);
        SubirDataEntity data6 = new SubirDataEntity();
        data6.setFecha("2023/03/22");
        data6.setQuincena("2023/03/2");
        data6.setTurno("M");
        data6.setProveedor("01003");
        data6.setKls_leche("55");
        subirDataRepository.save(data6);
        SubirDataEntity data7 = new SubirDataEntity();
        data7.setFecha("2023/03/23");
        data7.setQuincena("2023/03/2");
        data7.setTurno("M");
        data7.setProveedor("01003");
        data7.setKls_leche("56");
        subirDataRepository.save(data7);
        SubirDataEntity data8 = new SubirDataEntity();
        data8.setFecha("2023/03/24");
        data8.setQuincena("2023/03/2");
        data8.setTurno("M");
        data8.setProveedor("01003");
        data8.setKls_leche("57");
        subirDataRepository.save(data8);
        SubirDataEntity data9 = new SubirDataEntity();
        data9.setFecha("2023/03/25");
        data9.setQuincena("2023/03/2");
        data9.setTurno("M");
        data9.setProveedor("01003");
        data9.setKls_leche("58");
        subirDataRepository.save(data9);
        SubirDataEntity data10 = new SubirDataEntity();
        data10.setFecha("2023/03/26");
        data10.setQuincena("2023/03/2");
        data10.setTurno("M");
        data10.setProveedor("01003");
        data10.setKls_leche("59");
        subirDataRepository.save(data10);
        SubirDataEntity data11 = new SubirDataEntity();
        data11.setFecha("2023/03/27");
        data11.setQuincena("2023/03/2");
        data11.setTurno("M");
        data11.setProveedor("01003");
        data11.setKls_leche("60");
        subirDataRepository.save(data11);
        int result = Integer.parseInt(pagosService.calculoBonificacion("01003","2023/03/2"));
        assertEquals(12, result, 0.0);
        subirDataService.eliminarData();
    }

    @Test
    void testCalculoBonificacion3() {
        subirDataService.eliminarData();
        SubirDataEntity data1 = new SubirDataEntity();
        data1.setFecha("2023/03/17");
        data1.setQuincena("2023/03/2");
        data1.setTurno("M");
        data1.setProveedor("01003");
        data1.setKls_leche("50");
        subirDataRepository.save(data1);
        SubirDataEntity data2 = new SubirDataEntity();
        data2.setFecha("2023/03/18");
        data2.setQuincena("2023/03/2");
        data2.setTurno("T");
        data2.setProveedor("01003");
        data2.setKls_leche("51");
        subirDataRepository.save(data2);
        SubirDataEntity data3 = new SubirDataEntity();
        data3.setFecha("2023/03/19");
        data3.setQuincena("2023/03/2");
        data3.setTurno("M");
        data3.setProveedor("01003");
        data3.setKls_leche("52");
        subirDataRepository.save(data3);
        SubirDataEntity data4 = new SubirDataEntity();
        data4.setFecha("2023/03/20");
        data4.setQuincena("2023/03/2");
        data4.setTurno("M");
        data4.setProveedor("01003");
        data4.setKls_leche("53");
        subirDataRepository.save(data4);
        SubirDataEntity data5 = new SubirDataEntity();
        data5.setFecha("2023/03/21");
        data5.setQuincena("2023/03/2");
        data5.setTurno("M");
        data5.setProveedor("01003");
        data5.setKls_leche("54");
        subirDataRepository.save(data5);
        SubirDataEntity data6 = new SubirDataEntity();
        data6.setFecha("2023/03/22");
        data6.setQuincena("2023/03/2");
        data6.setTurno("M");
        data6.setProveedor("01003");
        data6.setKls_leche("55");
        subirDataRepository.save(data6);
        SubirDataEntity data7 = new SubirDataEntity();
        data7.setFecha("2023/03/23");
        data7.setQuincena("2023/03/2");
        data7.setTurno("M");
        data7.setProveedor("01003");
        data7.setKls_leche("56");
        subirDataRepository.save(data7);
        SubirDataEntity data8 = new SubirDataEntity();
        data8.setFecha("2023/03/24");
        data8.setQuincena("2023/03/2");
        data8.setTurno("M");
        data8.setProveedor("01003");
        data8.setKls_leche("57");
        subirDataRepository.save(data8);
        SubirDataEntity data9 = new SubirDataEntity();
        data9.setFecha("2023/03/25");
        data9.setQuincena("2023/03/2");
        data9.setTurno("M");
        data9.setProveedor("01003");
        data9.setKls_leche("58");
        subirDataRepository.save(data9);
        SubirDataEntity data10 = new SubirDataEntity();
        data10.setFecha("2023/03/26");
        data10.setQuincena("2023/03/2");
        data10.setTurno("M");
        data10.setProveedor("01003");
        data10.setKls_leche("59");
        subirDataRepository.save(data10);
        SubirDataEntity data11 = new SubirDataEntity();
        data11.setFecha("2023/03/27");
        data11.setQuincena("2023/03/2");
        data11.setTurno("M");
        data11.setProveedor("01003");
        data11.setKls_leche("60");
        subirDataRepository.save(data11);
        int result = Integer.parseInt(pagosService.calculoBonificacion("01003","2023/03/2"));
        assertEquals(20, result, 0.0);
        subirDataService.eliminarData();
    }

    @Test
    void testCalculoBonificacion4() {
        subirDataService.eliminarData();
        SubirDataEntity data1 = new SubirDataEntity();
        data1.setFecha("2023/03/17");
        data1.setQuincena("2023/03/2");
        data1.setTurno("T");
        data1.setProveedor("01003");
        data1.setKls_leche("50");
        subirDataRepository.save(data1);
        SubirDataEntity data2 = new SubirDataEntity();
        data2.setFecha("2023/03/18");
        data2.setQuincena("2023/03/2");
        data2.setTurno("T");
        data2.setProveedor("01003");
        data2.setKls_leche("51");
        subirDataRepository.save(data2);
        SubirDataEntity data3 = new SubirDataEntity();
        data3.setFecha("2023/03/19");
        data3.setQuincena("2023/03/2");
        data3.setTurno("T");
        data3.setProveedor("01003");
        data3.setKls_leche("52");
        subirDataRepository.save(data3);
        SubirDataEntity data4 = new SubirDataEntity();
        data4.setFecha("2023/03/20");
        data4.setQuincena("2023/03/2");
        data4.setTurno("T");
        data4.setProveedor("01003");
        data4.setKls_leche("53");
        subirDataRepository.save(data4);
        SubirDataEntity data5 = new SubirDataEntity();
        data5.setFecha("2023/03/21");
        data5.setQuincena("2023/03/2");
        data5.setTurno("T");
        data5.setProveedor("01003");
        data5.setKls_leche("54");
        subirDataRepository.save(data5);
        SubirDataEntity data6 = new SubirDataEntity();
        data6.setFecha("2023/03/22");
        data6.setQuincena("2023/03/2");
        data6.setTurno("T");
        data6.setProveedor("01003");
        data6.setKls_leche("55");
        subirDataRepository.save(data6);
        SubirDataEntity data7 = new SubirDataEntity();
        data7.setFecha("2023/03/23");
        data7.setQuincena("2023/03/2");
        data7.setTurno("T");
        data7.setProveedor("01003");
        data7.setKls_leche("56");
        subirDataRepository.save(data7);
        SubirDataEntity data8 = new SubirDataEntity();
        data8.setFecha("2023/03/24");
        data8.setQuincena("2023/03/2");
        data8.setTurno("T");
        data8.setProveedor("01003");
        data8.setKls_leche("57");
        subirDataRepository.save(data8);
        SubirDataEntity data9 = new SubirDataEntity();
        data9.setFecha("2023/03/25");
        data9.setQuincena("2023/03/2");
        data9.setTurno("T");
        data9.setProveedor("01003");
        data9.setKls_leche("58");
        subirDataRepository.save(data9);
        SubirDataEntity data10 = new SubirDataEntity();
        data10.setFecha("2023/03/26");
        data10.setQuincena("2023/03/2");
        data10.setTurno("T");
        data10.setProveedor("01003");
        data10.setKls_leche("59");
        subirDataRepository.save(data10);
        SubirDataEntity data11 = new SubirDataEntity();
        data11.setFecha("2023/03/27");
        data11.setQuincena("2023/03/2");
        data11.setTurno("T");
        data11.setProveedor("01003");
        data11.setKls_leche("60");
        subirDataRepository.save(data11);
        int result = Integer.parseInt(pagosService.calculoBonificacion("01003","2023/03/2"));
        assertEquals(8, result, 0.0);
        subirDataService.eliminarData();
    }

    @Test
    void testDescuentoXLeche1() {
        int result = Integer.parseInt(pagosService.descuentoXLeche("5"));
        assertEquals(0, result, 0.0);
    }

    @Test
    void testDescuentoXLeche2() {
        int result = Integer.parseInt(pagosService.descuentoXLeche("20"));
        assertEquals(7, result, 0.0);
    }

    @Test
    void testDescuentoXLeche3() {
        int result = Integer.parseInt(pagosService.descuentoXLeche("30"));
        assertEquals(15, result, 0.0);
    }

    @Test
    void testDescuentoXLeche4() {
        int result = Integer.parseInt(pagosService.descuentoXLeche("46"));
        assertEquals(30, result, 0.0);
    }

    @Test
    void testDescuentoXGrasa1() {
        int result = Integer.parseInt(pagosService.descuentoXGrasa("16"));
        assertEquals(12, result, 0.0);
    }

    @Test
    void testDescuentoXGrasa2() {
        int result = Integer.parseInt(pagosService.descuentoXGrasa("30"));
        assertEquals(20, result, 0.0);
    }

    @Test
    void testDescuentoXGrasa3() {
        int result = Integer.parseInt(pagosService.descuentoXGrasa("45"));
        assertEquals(30, result, 0.0);
    }

    @Test
    void testDescuentoXST1() {
        int result = Integer.parseInt(pagosService.descuentoXST("12"));
        assertEquals(18, result, 0.0);
    }

    @Test
    void testDescuentoXST2() {
        int result = Integer.parseInt(pagosService.descuentoXST("20"));
        assertEquals(27, result, 0.0);
    }

    @Test
    void testDescuentoXST3() {
        int result = Integer.parseInt(pagosService.descuentoXST("40"));
        assertEquals(45, result, 0.0);
    }

    @Test
    void testPagoAcopioLeche1() {
        int result = pagosService.pagoAcopioLeche("100000", "50000", "20000", "8");
        assertEquals(178000, result, 0.0);
    }

    @Test
    void testPagoAcopioLeche2() {
        int result = pagosService.pagoAcopioLeche("100000", "50000", "20000", "20");
        assertEquals(190000, result, 0.0);
    }

    @Test
    void testDescuentos1() {
        int result = pagosService.descuentos("7", "12", "18", 178000);
        assertEquals(65860, result, 0.0);
    }

    @Test
    void testRetencion1() {
        int result = pagosService.retencion(1000000, "Si");
        assertEquals(130000, result, 0.0);
    }

    @Test
    void testPagoFinal1() {
        int result = Integer.parseInt(pagosService.pagoFinal("112140", "14578"));
        assertEquals(97562, result, 0.0);
    }

    @Test
    void testguardarPagoDB1() {
        proveedorService.borrarTodos();
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo("01003");
        proveedor.setNombre("Leches Lukitas");
        proveedor.setCategoria("A");
        proveedor.setRetencion("Si");
        proveedorService.guardarProveedor(proveedor);
        subirDataService.eliminarData();
        SubirDataEntity data = new SubirDataEntity();
        data.setFecha("2023/03/16");
        data.setTurno("T");
        data.setProveedor("01003");
        data.setQuincena("2023/03/2");
        data.setKls_leche("45");
        subirDataService.guardarData(data);
        subirDetailsService.borrarTodos();
        SubirDetailsEntity details = new SubirDetailsEntity();
        details.setProveedor("01003");
        details.setGrasa("25");
        details.setSolido("14");
        subirDetailsService.guardarDetails(details);
        pagosService.borrarTodos();
        pagosService.guardarPagoDB(proveedor, data, details);

        assertNotNull(pagosService.obtenerByQuincena("2023/03/2"));

        proveedorService.borrarTodos();
        subirDataService.eliminarData();
        subirDetailsService.borrarTodos();
        pagosService.borrarTodos();
    }

    @Test
    void testQuincenaAnterior1() {
        assertNotNull(pagosService.quincenaAnterior("2024/09/2"));
    }

    @Test
    void testQuincenaAnterior2() {
        assertNotNull(pagosService.quincenaAnterior("2024/09/1"));
    }

    @Test
    void testQuincenaAnterior3() {
        assertNotNull(pagosService.quincenaAnterior("2024/01/1"));
    }

    @Test
    void testQuincenaAnterior4() {
        assertNull(pagosService.quincenaAnterior(""));
    }

    @Test
    void testCalcularVariacionNegativa1() {
        int result = Integer.parseInt(pagosService.calcularVariacionNegativa("15", "10"));
        assertEquals(0, result, 0.0);
    }

    @Test
    void testObtenerByProveedor1() {
        pagosService.borrarTodos();
        PagosEntity pago1 = new PagosEntity();
        pago1.setQuincena("2023/03/1");
        pago1.setCodigo("01003");
        pago1.setKlsLeche("120");
        pagosService.guardarPago(pago1);
        assertEquals(1, pagosService.obtenerByProveedor("01003").size(), 0.0);
        pagosService.borrarTodos();
    }

}
