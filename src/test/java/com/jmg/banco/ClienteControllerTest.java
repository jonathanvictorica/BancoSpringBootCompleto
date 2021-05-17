package com.jmg.banco;

import com.jmg.banco.controller.dto.ClienteDTO;
import com.jmg.banco.controller.dto.ClienteResponseDTO;
import com.jmg.banco.controller.impl.ClienteControllerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ClienteControllerImpl.class)
public class ClienteControllerTest extends TestGeneric {

    void test0001_buscarClienteExistente() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/cliente/get/{id}", 1L).contentType("application/json")).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        ClienteResponseDTO cliente = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ClienteResponseDTO.class);
        assertEquals(cliente.getNombre(), "Jonathan");
    }

    @Test
    void test0002_craaryeliminarClienteRoberto() throws Exception {
        Long dnicliente = 250027L;

        // Creo el cliente
        ClienteDTO cliente = new ClienteDTO("DNI", dnicliente, "Roberto", "Victorica2");
        MvcResult mvcResult = mockMvc.perform(post("/cliente/create").contentType("application/json").header("Authorization", "Bearer " + TOKEN).content(objectMapper.writeValueAsString(cliente))).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.CREATED.value());

        // Busco el cliente por DNI
        mvcResult = mockMvc.perform(get("/cliente/getByDocumento/{nroDocumento}", dnicliente).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
        ClienteResponseDTO clienteResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ClienteResponseDTO.class);

        // Lo borro con el id obtenido
        mvcResult = mockMvc.perform(delete("/cliente/delete/{id}", clienteResponse.getId()).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
        assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
    }

}
