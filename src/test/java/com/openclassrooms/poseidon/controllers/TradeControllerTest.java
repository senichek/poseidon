package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.Trade;
import com.openclassrooms.poseidon.security.CustomOAuth2UserService;
import com.openclassrooms.poseidon.security.UserPrincipalDetailsService;
import com.openclassrooms.poseidon.services.TradeService;

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

@WebMvcTest(controllers = TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @MockBean
    private UserPrincipalDetailsService userPrincipalDetailsService;

    @MockBean
    private CustomOAuth2UserService oAuth2UserService;

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void homeTest() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void addTradeFormTest() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void validateTest() throws Exception {
        mockMvc.perform(post("/trade/validate?tradeId=5&account=trAccount&type=trType&buyQuantity=10.0&sellQuantity=12.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void validateFailTest() throws Exception {
        mockMvc.perform(post("/trade/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void updateTest() throws Exception {
        mockMvc.perform(post("/trade/update/1?tradeId=5&account=trAccount&type=trType&buyQuantity=10.0&sellQuantity=12.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void updateFailTest() throws Exception {
        mockMvc.perform(post("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void deleteTest() throws Exception {
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void showUpdateFormTest() throws Exception {
        Trade trade = new Trade();

        when(tradeService.getById(any(Integer.class))).thenReturn(trade);

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andDo(print());
    }
}
