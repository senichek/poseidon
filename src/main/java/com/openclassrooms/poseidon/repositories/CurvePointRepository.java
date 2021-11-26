package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.CurvePoint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

    @Query(value = "SELECT DISTINCT c FROM CurvePoint c WHERE c.id=:id")
    CurvePoint findByCurvePointId(Integer id);
}
