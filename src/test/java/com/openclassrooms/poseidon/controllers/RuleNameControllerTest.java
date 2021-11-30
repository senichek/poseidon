package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.domain.RuleName;
import com.openclassrooms.poseidon.security.UserPrincipalDetailsService;
import com.openclassrooms.poseidon.services.RuleNameService;

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

@WebMvcTest(controllers = RuleNameController.class)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @MockBean
    private UserPrincipalDetailsService userPrincipalDetailsService;

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void homeTest() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void addRuleFormTest() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void validateTest() throws Exception {
        mockMvc.perform(post("/ruleName/validate?name=rName&description=rDesc&json=rJson&template=rTemplate"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void validateFailTest() throws Exception {
        mockMvc.perform(post("/ruleName/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void updateTest() throws Exception {
        mockMvc.perform(post("/ruleName/update/1?name=rName&description=rDesc&json=rJson&template=rTemplate"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void updateFailTest() throws Exception {
        mockMvc.perform(post("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void deleteTest() throws Exception {
        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "Admin", password = "Pass11111#")
    public void showUpdateFormTest() throws Exception {
        RuleName ruleName = new RuleName();

        when(ruleNameService.getById(any(Integer.class))).thenReturn(ruleName);

        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andDo(print());
    }
}
