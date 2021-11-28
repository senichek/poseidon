package com.openclassrooms.poseidon.controllers;

import javax.validation.Valid;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.services.TradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.getAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            if (result.getFieldError("account") != null) {
                model.addAttribute("account", result.getFieldError("account").getDefaultMessage());
            }
            if (result.getFieldError("type") != null) {
                model.addAttribute("type", result.getFieldError("type").getDefaultMessage());
            }
            if (result.getFieldError("buyQuantity") != null) {
                model.addAttribute("buyQuantity", result.getFieldError("buyQuantity").getDefaultMessage());
            }
            if (result.getFieldError("sellQuantity") != null) {
                model.addAttribute("sellQuantity", result.getFieldError("sellQuantity").getDefaultMessage());
            }
            return "trade/add";
        } else {
            tradeService.create(trade);
            return "redirect:/trade/list";
        }
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws Exception {
        model.addAttribute("trade", tradeService.getById(id));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
            BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            if (result.getFieldError("account") != null) {
                model.addAttribute("account", result.getFieldError("account").getDefaultMessage());
            }
            if (result.getFieldError("type") != null) {
                model.addAttribute("type", result.getFieldError("type").getDefaultMessage());
            }
            if (result.getFieldError("buyQuantity") != null) {
                model.addAttribute("buyQuantity", result.getFieldError("buyQuantity").getDefaultMessage());
            }
            if (result.getFieldError("sellQuantity") != null) {
                model.addAttribute("sellQuantity", result.getFieldError("sellQuantity").getDefaultMessage());
            }
            return "trade/update";
        } else {
            tradeService.update(trade);
            return "redirect:/trade/list";
        }
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) throws Exception {
        tradeService.delete(id);
        return "redirect:/trade/list";
    }
}
