package com.openclassrooms.poseidon.controllers;

import javax.validation.Valid;

import com.openclassrooms.poseidon.domain.Rating;
import com.openclassrooms.poseidon.services.RatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.getAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            if (result.getFieldError("moodysRating") != null) {
                model.addAttribute("moodysRating", result.getFieldError("moodysRating").getDefaultMessage());
            }
            if (result.getFieldError("sandPRating") != null) {
                model.addAttribute("sandPRating", result.getFieldError("sandPRating").getDefaultMessage());
            }
            if (result.getFieldError("fitchRating") != null) {
                model.addAttribute("fitchRating", result.getFieldError("fitchRating").getDefaultMessage());
            }
            if (result.getFieldError("orderNumber") != null) {
                model.addAttribute("orderNumber", result.getFieldError("orderNumber").getDefaultMessage());
            }
            return "rating/add";
        } else {
            ratingService.create(rating);
            return "redirect:/rating/list";
        }
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws Exception {
        model.addAttribute("rating", ratingService.getById(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model)
            throws Exception {
        if (result.hasErrors()) {
            if (result.getFieldError("moodysRating") != null) {
                model.addAttribute("moodysRating", result.getFieldError("moodysRating").getDefaultMessage());
            }
            if (result.getFieldError("sandPRating") != null) {
                model.addAttribute("sandPRating", result.getFieldError("sandPRating").getDefaultMessage());
            }
            if (result.getFieldError("fitchRating") != null) {
                model.addAttribute("fitchRating", result.getFieldError("fitchRating").getDefaultMessage());
            }
            if (result.getFieldError("orderNumber") != null) {
                model.addAttribute("orderNumber", result.getFieldError("orderNumber").getDefaultMessage());
            }
            return "rating/update";
        } else {
            ratingService.update(rating);
            return "redirect:/rating/list";
        }
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) throws Exception {
        ratingService.delete(id);
        return "redirect:/rating/list";
    }
}
