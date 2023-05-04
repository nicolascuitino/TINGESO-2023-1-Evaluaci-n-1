package com.tingeso.eval1.repositories;

import com.tingeso.eval1.entities.SubirDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface SubirDataRepository extends JpaRepository <SubirDataEntity, Integer>{

    ArrayList<SubirDataEntity> findByProveedor(String proveedor);
}
