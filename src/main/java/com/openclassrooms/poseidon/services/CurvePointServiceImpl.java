package com.openclassrooms.poseidon.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.repositories.CurvePointRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CurvePointServiceImpl implements CurvePointService {

    @Autowired
    CurvePointRepository curvePointRepository;

    @Override
    public CurvePoint create(CurvePoint cPoint) throws Exception {
        if (cPoint.getCurveId() == null) {
            throw new Exception("CurveID is null.");
        } else if (cPoint.getTerm() < 0 || cPoint.getValue() < 0 || cPoint.getCurveId() < 0) {
            throw new Exception("Curve ID, Term or Value cannot be negative.");
        } else {
            cPoint.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
            CurvePoint created = curvePointRepository.save(cPoint);
            log.info("Created {}.", created);
            return created; 
        }
    }

    @Override
    public List<CurvePoint> getAll() {
        return curvePointRepository.findAll();
    }

    @Override
    public CurvePoint getById(Integer id) throws Exception {
        CurvePoint cPoint = curvePointRepository.findByCurvePointId(id);
        if (cPoint == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", id));
        } else {
            return cPoint;
        }
    }

    @Override
    @Transactional
    public CurvePoint update(CurvePoint cPoint) throws Exception {
        CurvePoint toUpdate = curvePointRepository.findByCurvePointId(cPoint.getId());
        if (toUpdate == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", cPoint.getId()));
        } else if (cPoint.getTerm() < 0 || cPoint.getValue() < 0 || cPoint.getCurveId() < 0) {
            throw new Exception("Curve ID, Term or Value cannot be negative.");
        } else {
            if (cPoint.getCurveId() != toUpdate.getCurveId()) {
                toUpdate.setCurveId(cPoint.getCurveId());
            }
            if (cPoint.getTerm() != toUpdate.getTerm()) {
                toUpdate.setTerm(cPoint.getTerm());
            }
            if (cPoint.getValue() != toUpdate.getValue()) {
                toUpdate.setValue(cPoint.getValue());
            }
            curvePointRepository.save(toUpdate);
            log.info("Updated {}", toUpdate);
            return toUpdate;
        }
    }

    @Override
    public CurvePoint delete(Integer id) throws Exception {
        CurvePoint toDelete = curvePointRepository.findByCurvePointId(id);
        if (toDelete == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", id));
        } else {
            curvePointRepository.delete(toDelete);
            log.info("Deleted {}.", toDelete);
            return toDelete;
        }
    }
}
