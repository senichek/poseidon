package com.openclassrooms.poseidon.services;

import java.util.List;
import com.openclassrooms.poseidon.domain.Trade;

public interface TradeService {
    Trade create(Trade tr) throws Exception;
    Trade update(Trade tr) throws Exception;
    Trade delete(Integer id) throws Exception;
    List<Trade> getAll();
    Trade getById(Integer id) throws Exception;
}
