package com.openclassrooms.poseidon.controllers;

import javax.validation.Valid;

import com.openclassrooms.poseidon.domain.User;
import com.openclassrooms.poseidon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users/list")
    public String home(Model model) {
        model.addAttribute("users", userService.getAll());
        return "user/list";
    }

    // TODO удалить неиспользуемый параметр и в других контроллерах тоже.
    // Добавить хендлер эксепшенов
    // TODO добавить no permisson page
    @GetMapping("/users/add")
    public String addTrade(User user) {
        return "user/add";
    }

    @PostMapping("/users/validate")
    public String validate(@Valid User user, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            if (result.getFieldError("username") != null) {
                model.addAttribute("username", result.getFieldError("username").getDefaultMessage());
            }
            if (result.getFieldError("fullname") != null) {
                model.addAttribute("fullname", result.getFieldError("fullname").getDefaultMessage());
            }
            if (result.getFieldError("rawPassword") != null) {
                model.addAttribute("rawPassword", result.getFieldError("rawPassword").getDefaultMessage());
            }
            if (result.getFieldError("role") != null) {
                model.addAttribute("role", result.getFieldError("role").getDefaultMessage());
            }
            return "user/add";
        } else {
            userService.create(user);
            return "redirect:/users/list";
        }
    }

    @GetMapping("/users/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws Exception {
        model.addAttribute("user", userService.getById(id));
        return "user/update";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
            BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            if (result.getFieldError("username") != null) {
                model.addAttribute("username", result.getFieldError("username").getDefaultMessage());
            }
            if (result.getFieldError("fullname") != null) {
                model.addAttribute("fullname", result.getFieldError("fullname").getDefaultMessage());
            }
            if (result.getFieldError("role") != null) {
                model.addAttribute("role", result.getFieldError("role").getDefaultMessage());
            }
            return "user/update";
        } else {
            userService.update(user);
            return "redirect:/users/list";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) throws Exception {
        userService.delete(id);
        return "redirect:/users/list";
    }
}
