package com.openclassrooms.poseidon.services;

import java.util.List;

import com.openclassrooms.poseidon.domain.BidList;

public interface BidListService {
    BidList create(BidList bList) throws Exception;
    BidList update(BidList bList) throws Exception;
    BidList delete(Integer id) throws Exception;
    List<BidList> getAll();
    BidList getById(Integer id) throws Exception;

}
