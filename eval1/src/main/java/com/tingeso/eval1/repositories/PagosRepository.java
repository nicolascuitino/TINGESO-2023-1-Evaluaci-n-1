package com.tingeso.eval1.repositories;

import com.tingeso.eval1.entities.PagosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PagosRepository extends JpaRepository<PagosEntity, Integer> {

    ArrayList<PagosEntity> findByQuincena(String quincena);

    ArrayList<PagosEntity> findByCodigo(String codigo);
}
