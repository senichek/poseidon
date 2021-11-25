package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.BidList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

    @Query(value = "SELECT DISTINCT b FROM BidList b WHERE b.bidListId=:id")
    BidList findByBidListId(Integer id);
}
