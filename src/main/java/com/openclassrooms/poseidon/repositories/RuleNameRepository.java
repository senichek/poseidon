package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.domain.RuleName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {

    @Query(value = "SELECT DISTINCT r FROM RuleName r WHERE r.id=:id")
    RuleName findByRuleId(Integer id);
}
