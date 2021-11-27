package com.openclassrooms.poseidon.services;

import java.util.List;
import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RuleNameServiceImpl implements RuleNameService {

    @Autowired
    private RuleNameRepository ruleRepository;

    @Override
    public RuleName create(RuleName rl) throws Exception {
        if (rl.getName() == null || rl.getName().isEmpty()) {
            throw new Exception("Name is empty or null.");
        } else if (rl.getDescription() == null || rl.getDescription().isEmpty()) {
            throw new Exception("Description is empty or null.");
        } else if (rl.getJson() == null || rl.getJson().isEmpty()) {
            throw new Exception("Json is empty or null.");
        } else if (rl.getTemplate() == null || rl.getTemplate().isEmpty()) {
            throw new Exception("Template is empty or null.");
        } else {
            RuleName created = ruleRepository.save(rl);
            log.info("Created {}", created);
            return created;
        }
    }

    @Override
    public RuleName update(RuleName rl) throws Exception {
        RuleName toUpdate = ruleRepository.findByRuleId(rl.getId());
        if (toUpdate == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", rl.getId()));
        } else if (rl.getName() == null || rl.getName().isEmpty()) {
            throw new Exception("Name is empty or null.");
        } else if (rl.getDescription() == null || rl.getDescription().isEmpty()) {
            throw new Exception("Description is empty or null.");
        } else if (rl.getJson() == null || rl.getJson().isEmpty()) {
            throw new Exception("Json is empty or null.");
        } else if (rl.getTemplate() == null || rl.getTemplate().isEmpty()) {
            throw new Exception("Template is empty or null.");
        } else {
            if (!rl.getName().equals(toUpdate.getName())) {
                toUpdate.setName(rl.getName());
            }
            if (!rl.getDescription().equals(toUpdate.getDescription())) {
                toUpdate.setDescription(rl.getDescription());
            }
            if (!rl.getJson().equals(toUpdate.getJson())) {
                toUpdate.setJson(rl.getJson());
            }
            if (!rl.getTemplate().equals(toUpdate.getTemplate())) {
                toUpdate.setTemplate(rl.getTemplate());
            }
            ruleRepository.save(toUpdate);
            log.info("Updated {}", toUpdate);
            return toUpdate;
        }
    }

    @Override
    public RuleName delete(Integer id) throws Exception {
        RuleName toDelete = ruleRepository.findByRuleId(id);
        if (toDelete == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", id));
        } else {
            ruleRepository.delete(toDelete);
            log.info("Deleted {}.", toDelete);
            return toDelete;
        }
    }

    @Override
    public List<RuleName> getAll() {
        return ruleRepository.findAll();
    }

    @Override
    public RuleName getById(Integer id) throws Exception {
        RuleName ruleName = ruleRepository.findByRuleId(id);
        if (ruleName == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", id));
        } else {
            return ruleName;
        }
    }
}
