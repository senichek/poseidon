package com.openclassrooms.poseidon.services;

import java.util.List;

import com.openclassrooms.poseidon.domain.CurvePoint;

public interface CurvePointService {
    CurvePoint create(CurvePoint cPoint) throws Exception;
    CurvePoint update(CurvePoint cPoint) throws Exception;
    CurvePoint delete(Integer id) throws Exception;
    List<CurvePoint> getAll();
    CurvePoint getById(Integer id) throws Exception;
}
