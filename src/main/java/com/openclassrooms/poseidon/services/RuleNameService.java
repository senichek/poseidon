package com.openclassrooms.poseidon.services;

import java.util.List;
import com.openclassrooms.poseidon.domain.RuleName;

public interface RuleNameService {
    RuleName create(RuleName rl) throws Exception;
    RuleName update(RuleName rl) throws Exception;
    RuleName delete(Integer id) throws Exception;
    List<RuleName> getAll();
    RuleName getById(Integer id) throws Exception;
}
