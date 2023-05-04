package com.tingeso.eval1.controllers;

import com.tingeso.eval1.entities.PagosEntity;
import com.tingeso.eval1.entities.ProveedorEntity;
import com.tingeso.eval1.entities.SubirDataEntity;
import com.tingeso.eval1.entities.SubirDetailsEntity;
import com.tingeso.eval1.services.PagosService;
import com.tingeso.eval1.services.ProveedorService;
import com.tingeso.eval1.services.SubirDataService;
import com.tingeso.eval1.services.SubirDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping
public class PagosController {
    @Autowired
    PagosService pagosService;

    @Autowired
    SubirDataService subirDataService;

    @Autowired
    SubirDetailsService subirDetailsService;

    @Autowired
    ProveedorService proveedorService;

    @GetMapping("/pagos")
    public String listarPagos(Model model) {
        mostrarPagos();
        ArrayList<PagosEntity> totalPagos = pagosService.obtenerPagos();
        model.addAttribute("pagos", totalPagos);
        return "pagos";
    }

    //@PostMapping("/pagos")
    public void mostrarPagos(){

        ArrayList<SubirDataEntity> subirData = subirDataService.obtenerData();
        ArrayList<SubirDetailsEntity> subirDetails = subirDetailsService.obtenerDetails();

        boolean exists = false;

            for(SubirDataEntity j: subirData){
                ArrayList<PagosEntity> pagos = pagosService.obtenerPagos();
                for(PagosEntity k: pagos){
                    if(k.getCodigo().equals(j.getProveedor()) &&
                            j.getQuincena().equals(k.getQuincena())){
                        exists = true;
                    }

                }
                if(exists == false){
                    pagosService.guardarPagoDB(proveedorService.encontrarCodigo(j.getProveedor()),j,subirDetailsService.obtenerPorProveedor(j.getProveedor()));
                }
                exists = false;
            }


    }

}
