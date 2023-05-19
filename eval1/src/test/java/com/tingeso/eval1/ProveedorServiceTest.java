package com.tingeso.eval1;

import com.tingeso.eval1.services.ProveedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProveedorServiceTest {
    @Autowired
    ProveedorService proveedorService;

    @Test
    void testGuardarProveedorArg(){
        proveedorService.borrarTodos();
        proveedorService.guardarProveedorArg("01003", "Soprole", "A", "Si");
        assertNotNull(proveedorService.obtenerProveedores().get(0));
        proveedorService.borrarTodos();
    }
}
