package com.jmg.banco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmg.banco.exception.ServicioException;
import com.jmg.banco.service.AccionesAutenticarUseCase;

@RestController
public class SeguridadController {

	private AccionesAutenticarUseCase accionesCuentaBancariaUseCase;

	@Autowired
	public SeguridadController(AccionesAutenticarUseCase accionesCuentaBancariaUseCase) {
		super();
		this.accionesCuentaBancariaUseCase = accionesCuentaBancariaUseCase;
	}

	@PostMapping(path = "/seguridad/autenticar/{usuario}/{password}")
	public String autenticar(@PathVariable(name = "usuario") String usuario, @PathVariable(name = "password") String password) throws ServicioException {
		return accionesCuentaBancariaUseCase.generarTokenAutenticacion(usuario, password);
	}
}
