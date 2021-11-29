package com.openclassrooms.poseidon.controllers;

import javax.validation.Valid;

import com.openclassrooms.poseidon.domain.User;
import com.openclassrooms.poseidon.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @PostMapping("/home")
    public String showHome() {
        return "redirect:/bidList/list";
    }
    
    @GetMapping("/login")
    public String showLogin(@RequestParam(name="error", required=false) String error, Model model) {
        if (error!=null && error.equals("true")) {
            model.addAttribute("error", "Failed to log you in. Make sure the account exists and the credentials are correct.");
        }
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(/* @ModelAttribute */ @Valid User user, BindingResult result, Model model) throws Exception {
        // The default ROLE is USER, it is set on html registration page. 
        // You cannot register yourself as Admin, it's the Admins of the 
        // app who can assign you the role of ADMIN.
        user.setRole("USER");
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
            return "registration";
        } else {
            User usr = userService.create(user);
            // If a user with this email exists we return null.
            if (usr == null) {
                model.addAttribute("userExists", "Account exists. You can log in");
                return "registration";
            } else {
                return "redirect:/login";
            }
        }  
    }

    @GetMapping(value = "/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }
}
