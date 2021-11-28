package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.Trade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

    @Query(value = "SELECT DISTINCT t FROM Trade t WHERE t.tradeId=:id")
    Trade findByTradeId(Integer id);
}
