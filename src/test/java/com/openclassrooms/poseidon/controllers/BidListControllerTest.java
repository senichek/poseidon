package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.security.CustomOAuth2UserService;
import com.openclassrooms.poseidon.security.UserPrincipalDetailsService;
import com.openclassrooms.poseidon.services.BidListService;

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

@WebMvcTest(controllers = BidListController.class)
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    @MockBean
    private UserPrincipalDetailsService userPrincipalDetailsService;

    @MockBean
    private CustomOAuth2UserService oAuth2UserService;

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void homeTest() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void addBidFormTest() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void validateTest() throws Exception {
        mockMvc.perform(post("/bidList/validate?account=Account&type=Type&bidQuantity=12.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void validateFailTest() throws Exception {
        mockMvc.perform(post("/bidList/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void updateTest() throws Exception {
        mockMvc.perform(post("/bidList/update/1?account=Account&type=Type&bidQuantity=12.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void updateFailTest() throws Exception {
        mockMvc.perform(post("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void deleteTest() throws Exception {
        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void showUpdateFormTest() throws Exception {
        BidList bidList = new BidList();

        when(bidListService.getById(any(Integer.class))).thenReturn(bidList);

        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andDo(print());
    }
}
