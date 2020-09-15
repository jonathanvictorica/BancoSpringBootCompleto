package com.jmg.banco;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jmg.banco.controller.impl.ClienteControllerImpl;
import com.jmg.banco.domain.Cliente;
import com.jmg.banco.domain.CuentaBancaria;

@WebMvcTest(controllers = ClienteControllerImpl.class)
@Transactional
public class CuentaBancariaControllerTest extends TestGeneric {

//	void test0001_buscarClienteExistente() throws Exception {
//		MvcResult mvcResult = mockMvc.perform(get("/cliente/get/{id}", 1L).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
//		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
//		Cliente cliente = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Cliente.class);
//		assertEquals(cliente.getNombre(), "Jonathan");
//	}

	@Test
	void test0001_craarCuentayOperoyEliminar() throws Exception {
		Long dni_Roberto = 250022L;
		Long dni_Jonathan = 250023L;

		String aliasCuentaRoberto = "CA.PESOS.ROB";
		String aliasCuentaJonathan = "CA.PESOS.JON";

		Cliente clienteRoberto = new Cliente("DNI", dni_Roberto, "Roberto", "Victorica2", new Date());
		Cliente clienteJonathan = new Cliente("DNI", dni_Jonathan, "Jonathan", "Martinez", new Date());

		// Creo ambos clientes

		// Creo a Roberto
		MvcResult responseCrearClienteRoberto = mockMvc.perform(post("/cliente/create").header("Authorization", "Bearer " + TOKEN).contentType("application/json").content(objectMapper.writeValueAsString(clienteRoberto))).andReturn();
		assertEquals(responseCrearClienteRoberto.getResponse().getStatus(), HttpStatus.CREATED.value());

		// Creo a Jonathan
		MvcResult responseCrearClienteJonathan = mockMvc.perform(post("/cliente/create").header("Authorization", "Bearer " + TOKEN).contentType("application/json").content(objectMapper.writeValueAsString(clienteJonathan))).andReturn();
		assertEquals(responseCrearClienteRoberto.getResponse().getStatus(), HttpStatus.CREATED.value());

		// Busco el cliente Roberto por DNI
		MvcResult buscarClienteRoberto = mockMvc.perform(get("/cliente/getByDocumento/{nroDocumento}", dni_Roberto).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
		assertEquals(buscarClienteRoberto.getResponse().getStatus(), HttpStatus.OK.value());
		clienteRoberto = objectMapper.readValue(buscarClienteRoberto.getResponse().getContentAsString(), Cliente.class);

		// Busco el cliente Jonathan por DNI
		MvcResult buscarClienteJonathan = mockMvc.perform(get("/cliente/getByDocumento/{nroDocumento}", dni_Jonathan).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
		assertEquals(buscarClienteJonathan.getResponse().getStatus(), HttpStatus.OK.value());
		clienteJonathan = objectMapper.readValue(buscarClienteJonathan.getResponse().getContentAsString(), Cliente.class);

		// Creo la cuenta bancaria para Roberto
		CuentaBancaria cuentaRoberto = new CuentaBancaria(clienteRoberto, new Date(), new BigDecimal("3000"), aliasCuentaRoberto, null, new Date());
		MvcResult crearCuentaRoberto = mockMvc.perform(post("/account/create").header("Authorization", "Bearer " + TOKEN).contentType("application/json").content(objectMapper.writeValueAsString(cuentaRoberto))).andReturn();
		assertEquals(crearCuentaRoberto.getResponse().getStatus(), HttpStatus.CREATED.value());

		CuentaBancaria cuentaJonathan = new CuentaBancaria(clienteJonathan, new Date(), new BigDecimal("3000"), aliasCuentaJonathan, null, new Date());
		MvcResult crearCuentaJonathan = mockMvc.perform(post("/account/create").header("Authorization", "Bearer " + TOKEN).contentType("application/json").content(objectMapper.writeValueAsString(cuentaJonathan))).andReturn();
		assertEquals(crearCuentaJonathan.getResponse().getStatus(), HttpStatus.CREATED.value());

		// Busco ambas cuentas recien creadas
		MvcResult buscarCuentaRoberto = mockMvc.perform(get("/account/getByCliente/{idCliente}", clienteRoberto.getId()).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
		assertEquals(buscarCuentaRoberto.getResponse().getStatus(), HttpStatus.OK.value());
		cuentaRoberto = ((List<CuentaBancaria>) new Gson().fromJson(buscarCuentaRoberto.getResponse().getContentAsString(), new TypeToken<List<CuentaBancaria>>() {
		}.getType())).get(0);

		MvcResult buscarCuentaJonathan = mockMvc.perform(get("/account/getByCliente/{idCliente}", clienteJonathan.getId()).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
		assertEquals(buscarCuentaJonathan.getResponse().getStatus(), HttpStatus.OK.value());
		cuentaJonathan = ((List<CuentaBancaria>) new Gson().fromJson(buscarCuentaJonathan.getResponse().getContentAsString(), new TypeToken<List<CuentaBancaria>>() {
		}.getType())).get(0);

		// Realizar el debito desde Roberto a Jonathan por el monto de $2000
		MvcResult transferenciaJonaARober2000 = mockMvc.perform(
				put("/account/debit/{idCuentaBancariaDebitar}/{idCuentaBancariaDestino}/{importe}", cuentaRoberto.getId(), cuentaJonathan.getId(), new BigDecimal("2000")).header("Authorization", "Bearer " + TOKEN).contentType("application/json"))
				.andReturn();
		assertEquals(buscarCuentaJonathan.getResponse().getStatus(), HttpStatus.OK.value());

		// Consultamos el Saldo de la cuenta de Roberto que debe tener $1000
		MvcResult buscarSaldoRoberto = mockMvc.perform(get("/account/balance/{idCuentaBancaria}", cuentaRoberto.getId()).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
		assertEquals(buscarSaldoRoberto.getResponse().getStatus(), HttpStatus.OK.value());
		BigDecimal saldoRoberto = objectMapper.readValue(buscarSaldoRoberto.getResponse().getContentAsString(), BigDecimal.class);
		assertEquals(saldoRoberto.toBigInteger(), new BigInteger("1000"));

		// Consultamos el Saldo de la cuenta de Jonathan que debe tener $5000
		MvcResult buscarSaldoJonathan = mockMvc.perform(get("/account/balance/{idCuentaBancaria}", cuentaJonathan.getId()).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
		assertEquals(buscarSaldoJonathan.getResponse().getStatus(), HttpStatus.OK.value());
		BigDecimal saldoJonathan = objectMapper.readValue(buscarSaldoJonathan.getResponse().getContentAsString(), BigDecimal.class);
		assertEquals(saldoJonathan.toBigInteger(), new BigInteger("5000"));

		// Cerramos ambas cuentas
		MvcResult eliminarCuentaRoberto = mockMvc.perform(delete("/account/delete/{idCuentaBancaria}", cuentaRoberto.getId()).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
		assertEquals(eliminarCuentaRoberto.getResponse().getStatus(), HttpStatus.OK.value());

		MvcResult eliminarCuentaJonathan = mockMvc.perform(delete("/account/delete/{idCuentaBancaria}", cuentaJonathan.getId()).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
		assertEquals(eliminarCuentaJonathan.getResponse().getStatus(), HttpStatus.OK.value());

		// Borro ambos clientes
		MvcResult eliminarClienteRoberto = mockMvc.perform(delete("/cliente/delete/{id}", clienteRoberto.getId()).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
		assertEquals(eliminarClienteRoberto.getResponse().getStatus(), HttpStatus.OK.value());

		MvcResult eliminarClienteJonathan = mockMvc.perform(delete("/cliente/delete/{id}", clienteJonathan.getId()).header("Authorization", "Bearer " + TOKEN).contentType("application/json")).andReturn();
		assertEquals(eliminarClienteJonathan.getResponse().getStatus(), HttpStatus.OK.value());
	}

}
