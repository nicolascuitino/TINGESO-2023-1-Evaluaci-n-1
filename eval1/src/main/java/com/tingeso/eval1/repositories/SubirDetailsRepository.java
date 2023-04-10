package com.tingeso.eval1.repositories;

import com.tingeso.eval1.entities.SubirDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubirDetailsRepository extends JpaRepository<SubirDetailsEntity,Integer> {
}
