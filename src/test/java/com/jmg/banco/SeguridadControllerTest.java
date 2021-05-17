package com.jmg.banco;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmg.banco.controller.impl.ClienteControllerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ClienteControllerImpl.class)
public class SeguridadControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void test0001_seguridadBasica() throws Exception {
        String clave = "123";

        MvcResult mvcResult = mockMvc.perform(post("/seguridad/autenticar/{usuario}/{password}", "usrbanco", clave).contentType("application/json")).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        System.out.printf(mvcResult.getResponse().getContentAsString());

    }

}
