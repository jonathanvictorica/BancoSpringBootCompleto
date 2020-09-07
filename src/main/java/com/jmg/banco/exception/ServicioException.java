package com.jmg.banco.exception;

import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Access(value = AccessType.FIELD)
public class ServicioException extends Exception {

	private String mensaje;

	public ServicioException(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

}
