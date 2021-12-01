package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.CurvePoint;
import com.openclassrooms.poseidon.security.CustomOAuth2UserService;
import com.openclassrooms.poseidon.security.UserPrincipalDetailsService;
import com.openclassrooms.poseidon.services.CurvePointService;

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

@WebMvcTest(controllers = CurveController.class)
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    @MockBean
    private UserPrincipalDetailsService userPrincipalDetailsService;

    @MockBean
    private CustomOAuth2UserService oAuth2UserService;

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void homeTest() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void addBidFormTest() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void validateTest() throws Exception {
        mockMvc.perform(post("/curvePoint/validate?curveId=10&term=12.0&value=14.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void validateFailTest() throws Exception {
        mockMvc.perform(post("/curvePoint/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void updateTest() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1?curveId=10&term=12.0&value=14.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void updateFailTest() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void deleteTest() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void showUpdateFormTest() throws Exception {
        CurvePoint curvePoint = new CurvePoint();

        when(curvePointService.getById(any(Integer.class))).thenReturn(curvePoint);

        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andDo(print());
    }
}
