package com.openclassrooms.poseidon.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@SpringBootTest
public class ExceptionControllerTest {

    @Autowired
    ExceptionController exceptionController;

    private MockHttpServletRequest httpServletRequest;
    
    @Test
    public void defaultErrorHandlerTest() throws Exception {
        httpServletRequest = new MockHttpServletRequest("GET", "");
        httpServletRequest.setMethod("GET");

        Exception ex = new Exception("Test ex msg.");
        ModelAndView actual = exceptionController.defaultErrorHandler(httpServletRequest, ex);
        assertEquals("error", actual.getViewName());
    }
}
