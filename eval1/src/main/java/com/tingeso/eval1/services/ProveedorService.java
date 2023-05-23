package com.tingeso.eval1.services;

import com.tingeso.eval1.entities.ProveedorEntity;
import com.tingeso.eval1.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<ProveedorEntity> obtenerProveedores(){
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }

    public ProveedorEntity guardarProveedor(ProveedorEntity newProveedor){
        return proveedorRepository.save(newProveedor);
    }

    public void guardarProveedorArg(String codigo, String nombre, String categoria, String retencion){
        ProveedorEntity proveedor = new ProveedorEntity();
        proveedor.setCodigo(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria);
        proveedor.setRetencion(retencion);
        proveedorRepository.save(proveedor);
    }

    public Optional<ProveedorEntity> obtenerPorCodigo(String codigo){
        return proveedorRepository.findById(codigo);
    }

    public boolean eliminarProveedor(String codigo) {
        try{
            proveedorRepository.deleteById(codigo);
            return true;
        }catch(Exception err){
            return false;
        }
    }

    public ProveedorEntity encontrarCodigo(String codigo){return proveedorRepository.findByCodigo(codigo);}

    public void borrarTodos(){proveedorRepository.deleteAll();}

}
