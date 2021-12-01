package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.User;
import com.openclassrooms.poseidon.security.CustomOAuth2UserService;
import com.openclassrooms.poseidon.security.UserPrincipalDetailsService;
import com.openclassrooms.poseidon.services.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MainController.class)
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserPrincipalDetailsService userPrincipalDetailsService;

    @MockBean
    private CustomOAuth2UserService oAuth2UserService;

    @Test
    public void showHomeTest() throws Exception {
        mockMvc.perform(post("/home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andDo(print());
    }

    @Test
    public void showLoginTest() throws Exception {
        mockMvc.perform(get("/login?error=true"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andDo(print());
    }

    @Test
    public void showRegistrationTest() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andDo(print());
    }

    @Test
    public void registerUserFailTest() throws Exception {
        mockMvc.perform(post("/registration?username=John&fullname=Doe&rawPassword=Pass11111@&role=USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andDo(print());
    }

    @Test
    public void registerUserFailedValidationTest() throws Exception {
        mockMvc.perform(post("/registration?username=&fullname=&rawPassword=&role="))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andDo(print());
    }

    @Test
    public void registerUserSuccessTest() throws Exception {
        User user = new User();
        user.setUsername("John");
        user.setFullname("Doe");
        user.setRawPassword("Pass11111@");
        user.setRole("USER");
        when(userService.create(any(User.class))).thenReturn(user);
        mockMvc.perform(post("/registration?username=John&fullname=Doe&rawPassword=Pass11111@&role=USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "User", password = "Pass11111#")
    public void accessDeniedTest() throws Exception {
        mockMvc.perform(get("/accessDenied"))
                .andExpect(status().isOk())
                .andExpect(view().name("accessDenied"))
                .andDo(print());
    }

}
