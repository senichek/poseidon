package com.openclassrooms.poseidon.controllers;

import javax.validation.Valid;

import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.getAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            if (result.getFieldError("name") != null) {
                model.addAttribute("name", result.getFieldError("name").getDefaultMessage());
            }
            if (result.getFieldError("description") != null) {
                model.addAttribute("description", result.getFieldError("description").getDefaultMessage());
            }
            if (result.getFieldError("json") != null) {
                model.addAttribute("json", result.getFieldError("json").getDefaultMessage());
            }
            if (result.getFieldError("template") != null) {
                model.addAttribute("template", result.getFieldError("template").getDefaultMessage());
            }
            return "ruleName/add";
        } else {
            ruleNameService.create(ruleName);
            return "redirect:/ruleName/list";
        }
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws Exception {
        model.addAttribute("ruleName", ruleNameService.getById(id));
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
            BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            if (result.getFieldError("name") != null) {
                model.addAttribute("name", result.getFieldError("name").getDefaultMessage());
            }
            if (result.getFieldError("description") != null) {
                model.addAttribute("description", result.getFieldError("description").getDefaultMessage());
            }
            if (result.getFieldError("json") != null) {
                model.addAttribute("json", result.getFieldError("json").getDefaultMessage());
            }
            if (result.getFieldError("template") != null) {
                model.addAttribute("template", result.getFieldError("template").getDefaultMessage());
            }
            return "ruleName/update";
        } else {
            ruleNameService.update(ruleName);
            return "redirect:/ruleName/list";
        }
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) throws Exception {
        ruleNameService.delete(id);
        return "redirect:/ruleName/list";
    }
}
