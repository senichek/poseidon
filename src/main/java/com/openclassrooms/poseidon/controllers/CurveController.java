package com.openclassrooms.poseidon.controllers;

import javax.validation.Valid;

import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.services.CurvePointService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CurveController {

    @Autowired
    CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.getAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            if (result.getFieldError("curveId") != null) {
                model.addAttribute("curveId", result.getFieldError("curveId").getDefaultMessage());
            }
            if (result.getFieldError("term") != null) {
                model.addAttribute("term", result.getFieldError("term").getDefaultMessage());
            }
            if (result.getFieldError("value") != null) {
                model.addAttribute("value", result.getFieldError("value").getDefaultMessage());
            }
            return "curvePoint/add";
        } else {
            curvePointService.create(curvePoint);
            return "redirect:/curvePoint/list";
        }
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws Exception {
        model.addAttribute("curvePoint", curvePointService.getById(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
            Model model) throws Exception {
        if (result.hasErrors()) {
            if (result.getFieldError("curveId") != null) {
                model.addAttribute("curveId", result.getFieldError("curveId").getDefaultMessage());
            }
            if (result.getFieldError("term") != null) {
                model.addAttribute("term", result.getFieldError("term").getDefaultMessage());
            }
            if (result.getFieldError("value") != null) {
                model.addAttribute("value", result.getFieldError("value").getDefaultMessage());
            }
            return "curvePoint/update";
        } else {
            curvePointService.update(curvePoint);
            return "redirect:/curvePoint/list";
        }
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) throws Exception {
        curvePointService.delete(id);
        return "redirect:/curvePoint/list";
    }
}
