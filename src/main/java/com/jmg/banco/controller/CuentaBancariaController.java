package com.jmg.banco.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.annotation.HttpConstraint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jmg.banco.domain.CuentaBancaria;
import com.jmg.banco.exception.ServicioException;
import com.jmg.banco.service.AccionesCuentaBancariaUseCase;
import com.jmg.banco.service.AdmCuentaBancariaUseCase;
import com.jmg.banco.service.ConsultarCuentaBancariaUseCase;

@RestController
public class CuentaBancariaController {

	private ConsultarCuentaBancariaUseCase consultarCuentaBancariaUseCase;
	private AccionesCuentaBancariaUseCase accionesCuentaBancariaUseCase;
	private AdmCuentaBancariaUseCase admCuentaBancariaUseCase;

	@Autowired
	public CuentaBancariaController(ConsultarCuentaBancariaUseCase consultarCuentaBancariaUseCase, AccionesCuentaBancariaUseCase accionesCuentaBancariaUseCase, AdmCuentaBancariaUseCase admCuentaBancariaUseCase) {
		super();
		this.consultarCuentaBancariaUseCase = consultarCuentaBancariaUseCase;
		this.accionesCuentaBancariaUseCase = accionesCuentaBancariaUseCase;
		this.admCuentaBancariaUseCase = admCuentaBancariaUseCase;
	}

	@PostMapping(path = "/account/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Validated @RequestBody CuentaBancaria cuentaBancaria) throws ServicioException {
		admCuentaBancariaUseCase.crearCuentaBancaria(cuentaBancaria);
	}

	@PatchMapping(path = "/account/updateAlias/{idCuentaBancaria}/{alias}")
	public void updateAlias(@PathVariable(name = "idCuentaBancaria") Long id, @PathVariable(name = "alias") String aliasNuevo) throws ServicioException {
		admCuentaBancariaUseCase.modificarAlias(id, aliasNuevo);
	}

	@DeleteMapping(path = "/account/delete/{idCuentaBancaria}")
	public void delete(@PathVariable(name = "idCuentaBancaria") Long id) throws ServicioException {
		admCuentaBancariaUseCase.eliminarCuentaBancaria(id);
	}

	@PutMapping(path = "/account/debit/{idCuentaBancariaDebitar}/{idCuentaBancariaDestino}/{importe}")
	public void debit(@PathVariable(name = "idCuentaBancariaDebitar") Long idCuentaBancariaDebitar, @PathVariable(name = "idCuentaBancariaDestino") Long idCuentaBancariaDestino, @PathVariable(name = "importe") BigDecimal importe)
			throws ServicioException {
		accionesCuentaBancariaUseCase.debitar(idCuentaBancariaDebitar, idCuentaBancariaDestino, importe);
	}

	@PutMapping(path = "/account/debitauthorization/{idCuentaBancariaDebitar}/{idCuentaBancariaDestino}/{importe}/{autorizante}/{importeAutorizadoSobregiro}")
	public void debitAutoritation(@PathVariable(name = "idCuentaBancariaDebitar") Long idCuentaBancariaDebitar, @PathVariable(name = "idCuentaBancariaDestino") Long idCuentaBancariaDestino, @PathVariable(name = "importe") BigDecimal importe,
			@PathVariable(name = "autorizante") String autorizante, @PathVariable(name = "importeAutorizadoSobregiro") BigDecimal importeAutorizadoSobregiro) throws ServicioException {
		accionesCuentaBancariaUseCase.debitarConTopeSobregiro(idCuentaBancariaDebitar, idCuentaBancariaDestino, importe, autorizante, importeAutorizadoSobregiro);
	}

	@GetMapping(path = "/account/getByCliente/{idCliente}")
	public List<CuentaBancaria> getByIdCliente(@PathVariable(name = "idCliente") Long idCliente) {
		return consultarCuentaBancariaUseCase.listarCuentasBancariasPorIdCliente(idCliente);
	}

	@GetMapping(path = "/account/getByDocumentoCliente/{tipoDocumento}/{numeroDocumento}")
	public List<CuentaBancaria> getByDocumentoCliente(@PathVariable(name = "tipoDocumento") String tipoDocumento, @PathVariable(name = "numeroDocumento") String numeroDocumento) {
		return consultarCuentaBancariaUseCase.listarCuentasBancariasPorTipoYNumeroDocumentoCliente(tipoDocumento, numeroDocumento);
	}

	@GetMapping(path = "/account/getById/{idCuentaBancaria}")
	public CuentaBancaria getByIdCuentaBancaria(@PathVariable(name = "idCuentaBancaria") Long idCuentaBancaria) throws ServicioException {
		return consultarCuentaBancariaUseCase.obtenerCuentaBancariaPorId(idCuentaBancaria);
	}

	@GetMapping(path = "/account/balance/{idCuentaBancaria}")
	public BigDecimal getSaldoByIdCuentaBancaria(@PathVariable(name = "idCuentaBancaria") Long idCuentaBancaria) throws ServicioException {
		return consultarCuentaBancariaUseCase.obtenerSaldoCuenta(idCuentaBancaria);
	}

}
