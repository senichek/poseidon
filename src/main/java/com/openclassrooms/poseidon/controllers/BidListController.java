package com.openclassrooms.poseidon.controllers;

import javax.validation.Valid;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.services.BidListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BidListController {
    // TODO: Inject Bid service
    @Autowired
    BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        // TODO: call service find all bids to show to the view
        model.addAttribute("bidList", bidListService.getAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bList, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            if (result.getFieldError("account") != null) {
                model.addAttribute("account", result.getFieldError("account").getDefaultMessage());
            }
            if (result.getFieldError("type") != null) {
                model.addAttribute("type", result.getFieldError("type").getDefaultMessage());
            }
            if (result.getFieldError("bidQuantity") != null) {
                model.addAttribute("bidQuantity", result.getFieldError("bidQuantity").getDefaultMessage());
            }
            return "bidList/add";
        } else {
            bidListService.create(bList);
            return "redirect:/bidList/list";
        }
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws Exception {
        model.addAttribute("bidList", bidListService.getById(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            if (result.getFieldError("account") != null) {
                model.addAttribute("account", result.getFieldError("account").getDefaultMessage());
            }
            if (result.getFieldError("type") != null) {
                model.addAttribute("type", result.getFieldError("type").getDefaultMessage());
            }
            if (result.getFieldError("bidQuantity") != null) {
                model.addAttribute("bidQuantity", result.getFieldError("bidQuantity").getDefaultMessage());
            }
            return "bidList/update";
        } else {
            bidListService.update(bidList);
            return "redirect:/bidList/list";
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) throws Exception {
        bidListService.delete(id);
        return "redirect:/bidList/list";
    }
}
