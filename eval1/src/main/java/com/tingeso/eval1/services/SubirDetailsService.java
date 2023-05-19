package com.tingeso.eval1.services;

import com.tingeso.eval1.entities.SubirDetailsEntity;
import com.tingeso.eval1.repositories.SubirDataRepository;
import com.tingeso.eval1.repositories.SubirDetailsRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class SubirDetailsService {
    @Autowired
    SubirDetailsRepository subirDetailsRepository;

    @Autowired
    SubirDataRepository subirDataRepository;

    private final Logger logg = LoggerFactory.getLogger(SubirDataService.class);

    public ArrayList<SubirDetailsEntity> obtenerDetails(){
        return (ArrayList<SubirDetailsEntity>) subirDetailsRepository.findAll();
    }

    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        subirDetailsRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDetailsDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public void guardarDetails(SubirDetailsEntity details){ subirDetailsRepository.save(details);
    }

    public void guardarDetailsDB(String proveedor, String grasa, String solido){
        SubirDetailsEntity newDetail = new SubirDetailsEntity();
        newDetail.setProveedor(proveedor);
        newDetail.setGrasa(grasa);
        newDetail.setSolido(solido);
        guardarDetails(newDetail);
    }
    public void eliminarDetails(ArrayList<SubirDetailsEntity> details){
        subirDetailsRepository.deleteAll(details);
    }

    public SubirDetailsEntity obtenerPorProveedor(String proveedor){ return subirDetailsRepository.findByProveedor(proveedor);}

    public void borrarTodos(){subirDetailsRepository.deleteAll();}

}
