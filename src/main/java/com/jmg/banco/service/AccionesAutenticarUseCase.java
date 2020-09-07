package com.jmg.banco.service;

import com.jmg.banco.exception.ServicioException;

public interface AccionesAutenticarUseCase {

	public String generarTokenAutenticacion(String usuario, String password) throws ServicioException;
}
