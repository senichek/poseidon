package com.openclassrooms.poseidon.services;

import java.util.List;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public Trade create(Trade tr) throws Exception {
        if (tr.getAccount() == null || tr.getAccount().isEmpty()) {
            throw new Exception("Account is empty or null.");
        } else if (tr.getType() == null || tr.getType().isEmpty()) {
            throw new Exception("Type is empty or null.");
        } else if (tr.getBuyQuantity() == null || tr.getBuyQuantity() < 0) {
            throw new Exception("Buy Quantity must not be null or negative.");
        } else if (tr.getSellQuantity() == null || tr.getSellQuantity() < 0) {
            throw new Exception("Sell Quantity must not be null or negative.");
        } else {
            Trade created = tradeRepository.save(tr);
            log.info("Created {}", created);
            return created;
        }
    }

    @Override
    public Trade update(Trade tr) throws Exception {
        Trade toUpdate = tradeRepository.findByTradeId(tr.getTradeId());
        if (toUpdate == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", tr.getTradeId()));
        } else if (tr.getAccount() == null || tr.getAccount().isEmpty()) {
            throw new Exception("Account is empty or null.");
        } else if (tr.getType() == null || tr.getType().isEmpty()) {
            throw new Exception("Type is empty or null.");
        } else if (tr.getBuyQuantity() == null || tr.getBuyQuantity() < 0) {
            throw new Exception("Buy Quantity must not be null or negative.");
        } else if (tr.getSellQuantity() == null || tr.getSellQuantity() < 0) {
            throw new Exception("Sell Quantity must not be null or negative.");
        } else {
            if (!tr.getAccount().equals(toUpdate.getAccount())) {
                toUpdate.setAccount(tr.getAccount());
            }
            if (!tr.getType().equals(toUpdate.getType())) {
                toUpdate.setType(tr.getType());
            }
            if (tr.getBuyQuantity() != toUpdate.getBuyQuantity()) {
                toUpdate.setBuyQuantity(tr.getBuyQuantity());
            }
            if (tr.getSellQuantity() != toUpdate.getSellQuantity()) {
                toUpdate.setSellQuantity(tr.getSellQuantity());
            }
            tradeRepository.save(toUpdate);
            log.info("Updated {}", toUpdate);
            return toUpdate;
        }
    }

    @Override
    public Trade delete(Integer id) throws Exception {
        Trade toDelete = tradeRepository.findByTradeId(id);
        if (toDelete == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", id));
        } else {
            tradeRepository.delete(toDelete);
            log.info("Deleted {}.", toDelete);
            return toDelete;
        }
    }

    @Override
    public List<Trade> getAll() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade getById(Integer id) throws Exception {
        Trade trade = tradeRepository.findByTradeId(id);
        if (trade == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", id));
        } else {
            return trade;
        }
    }
}
