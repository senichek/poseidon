package com.openclassrooms.poseidon.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.repositories.BidListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BidListServiceImpl implements BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public BidList create(BidList bList) throws Exception {
        if (bList.getAccount().isEmpty() || bList.getType().isEmpty() || bList.getBidQuantity() == null) {
            throw new Exception("One of the fields [Account, Type, bidQuantity] is empty or null.");
        } else if (bList.getBidQuantity() < 0) {
            throw new Exception("Bid Quantity cannot be negative.");
        } else {
            bList.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
            BidList created = bidListRepository.save(bList);
            log.info("Created {}.", created);
            return created;
        }
    }

    @Override
    public List<BidList> getAll() {
        List<BidList> result = bidListRepository.findAll();

        Collections.sort(result, new Comparator<BidList>() {
            public int compare(BidList o1, BidList o2) {
                return o2.getCreationDate().compareTo(o1.getCreationDate());
            }
        });
        return result;
    }

    @Override
    @Transactional
    public BidList update(BidList bList) throws Exception {
        BidList toUpdate = bidListRepository.findByBidListId(bList.getBidListId());
        if (toUpdate == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", bList.getBidListId()));
        } else if (bList.getBidQuantity() < 0) {
            throw new Exception("Bid Quantity cannot be negative.");
        } else {
            if (!bList.getAccount().equals(toUpdate.getAccount()) && !bList.getAccount().equals("")) {
                toUpdate.setAccount(bList.getAccount());
            }
            if (!bList.getType().equals(toUpdate.getType()) && !bList.getType().equals("")) {
                toUpdate.setType(bList.getType());
            }
            if (bList.getBidQuantity() != toUpdate.getBidQuantity()) {
                toUpdate.setBidQuantity(bList.getBidQuantity());
            }
            bidListRepository.save(toUpdate);
            log.info("Updated {}", toUpdate);
            return toUpdate;
        }
    }

    @Override
    public BidList delete(Integer id) throws Exception {
        BidList toDelete = bidListRepository.findByBidListId(id);
        if (toDelete == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", id));
        } else {
            bidListRepository.delete(toDelete);
            log.info("Deleted {}.", toDelete);
            return toDelete;
        }
    }

    @Override
    public BidList getById(Integer id) throws Exception {
        BidList bidList = bidListRepository.findByBidListId(id);
        if (bidList == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", id));
        } else {
            return bidList;
        }
    }
}
