package com.jmg.banco.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jmg.banco.exception.ServicioException;
import com.jmg.banco.security.JwtTokenProvider;
import com.jmg.banco.service.AccionesAutenticarUseCase;

@Component
public class SeguridadService implements AccionesAutenticarUseCase {

	private PasswordEncoder passwordEncoder;

	private JwtTokenProvider jwtTokenProvider;

	private AuthenticationManager authenticationManager;

	@Autowired
	public SeguridadService(PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public String generarTokenAutenticacion(String usuario, String password) throws ServicioException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario, password));
			return jwtTokenProvider.createToken(usuario);
		} catch (AuthenticationException e) {
			throw new ServicioException("Invalid username/password supplied");
		}
	}

}
