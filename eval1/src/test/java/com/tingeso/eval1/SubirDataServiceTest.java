package com.tingeso.eval1;

import com.tingeso.eval1.services.SubirDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubirDataServiceTest {

    @Autowired
    SubirDataService subirDataService;

    @Test
    void testguardarDataDB1(){
        subirDataService.eliminarData();
        subirDataService.guardarDataDB("2023/05/16", "T", "01003", "120");
        assertNotNull(subirDataService.obtenerData().get(0));
        subirDataService.eliminarData();
    }
}
