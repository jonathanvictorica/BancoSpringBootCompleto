package com.jmg.banco;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import com.jmg.banco.controller.ClienteController;
import com.jmg.banco.domain.Cliente;

@WebMvcTest(controllers = ClienteController.class)
public class ClienteControllerTest extends TestGeneric{
	

	void test0001_buscarClienteExistente() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/cliente/get/{id}", 1L).contentType("application/json")).andReturn();
		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
		Cliente cliente = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cliente.class);
		assertEquals(cliente.getNombre(), "Jonathan");
	}

	@Test
	void test0002_craaryeliminarClienteRoberto() throws Exception {
		Long dnicliente = 250027L;

		// Creo el cliente
		Cliente cliente = new Cliente("DNI", dnicliente, "Roberto", "Victorica2", new Date());
		MvcResult mvcResult = mockMvc.perform(post("/cliente/create").contentType("application/json").header("Authorization", "Bearer " + TOKEN).content(objectMapper.writeValueAsString(cliente))).andReturn();
		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.CREATED.value());

		// Busco el cliente por DNI
		mvcResult = mockMvc.perform(get("/cliente/getByDocumento/{nroDocumento}", dnicliente).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
		cliente = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cliente.class);

		// Lo borro con el id obtenido
		mvcResult = mockMvc.perform(delete("/cliente/delete/{id}", cliente.getId()).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
	}

}
