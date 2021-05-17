package com.jmg.banco;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.annotation.PostConstruct;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TestGeneric {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected String TOKEN = null;

    /**
     * Para consumir los servicios con el token de seguridad mas abajo hay que
     * agregar despues del perform .header("Authorization", "Bearer " + TOKEN)
     *
     * @throws Exception
     */
    @PostConstruct
    public void generarToken() throws Exception {
        String clave = "123";
        MvcResult mvcResult = mockMvc.perform(post("/seguridad/autenticar/{usuario}/{password}", "usrbanco", clave).contentType("application/json")).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        TOKEN = mvcResult.getResponse().getContentAsString();
    }
}
