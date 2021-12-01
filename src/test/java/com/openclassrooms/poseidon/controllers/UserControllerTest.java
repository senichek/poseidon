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

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserPrincipalDetailsService userPrincipalDetailsService;

    @MockBean
    private CustomOAuth2UserService oAuth2UserService;

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#", roles = "ADMIN")
    // Only ADMIN can access the users' table.
    public void homeTest() throws Exception {
        mockMvc.perform(get("/users/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#", roles = "ADMIN")
    public void addUserFormTest() throws Exception {
        mockMvc.perform(get("/users/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#", roles = "ADMIN")
    public void validateTest() throws Exception {
        User user = new User();
        user.setUsername("John");
        user.setFullname("Doe");
        user.setRawPassword("Pass11111@");
        user.setRole("USER");
        when(userService.create(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/validate?username=John&fullname=Doe&rawPassword=Pass11111@&role=USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#", roles = "ADMIN")
    public void validateFailTest() throws Exception {
        mockMvc.perform(post("/users/validate?username=&fullname=&rawPassword=pass&role="))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#", roles = "ADMIN")
    public void validateUniqueNameTest() throws Exception {
        // If a username is already exists in DB, the "create" method will return Null.
        when(userService.create(any(User.class))).thenReturn(null);

        mockMvc.perform(post("/users/validate?username=Admin&fullname=Doe&rawPassword=Pass11111@&role=USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#", roles = "ADMIN")
    public void updateTest() throws Exception {
        User user = new User();
        user.setUsername("John");
        user.setFullname("Doe");
        user.setRawPassword("Pass11111@");
        user.setRole("USER");
        when(userService.update(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/update/1?username=John&fullname=Doe&rawPassword=Pass11111@&role=USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#", roles = "ADMIN")
    public void updateFailTest() throws Exception {
        mockMvc.perform(post("/users/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#", roles = "ADMIN")
    public void updateUniqueNameTest() throws Exception {
    // If a username is already exists in DB, the "update" method will return Null.
        when(userService.update(any(User.class))).thenReturn(null);

        mockMvc.perform(post("/users/update/1?username=John&fullname=Doe&rawPassword=Pass11111@&role=USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#", roles = "ADMIN")
    public void deleteTest() throws Exception {
        mockMvc.perform(get("/users/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#", roles = "ADMIN")
    public void showUpdateFormTest() throws Exception {
        User user = new User();

        when(userService.getById(any(Integer.class))).thenReturn(user);

        mockMvc.perform(get("/users/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andDo(print());
    }
}
