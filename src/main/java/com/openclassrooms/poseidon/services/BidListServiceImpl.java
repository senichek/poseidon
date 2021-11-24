package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.repositories.BidListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidListServiceImpl implements BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public BidList create(BidList bList) {
        return bidListRepository.save(bList);
    }
    
}
