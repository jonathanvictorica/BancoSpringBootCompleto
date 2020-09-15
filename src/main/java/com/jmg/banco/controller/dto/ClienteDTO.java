package com.jmg.banco.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClienteDTO {

	@Size(min = 2, max = 4)
	@NotNull
	private String tipoDocumento;

	@NotNull
	private Long nroDocumento;

	@Size(min = 1, max = 30)
	@NotNull
	private String nombre;

	@Size(min = 1, max = 50)
	@NotNull
	private String apellido;

	public ClienteDTO() {
		super();
	}

	public ClienteDTO(String tipoDocumento, Long nroDocumento, String nombre, String apellido) {
		super();
		this.tipoDocumento = tipoDocumento;
		this.nroDocumento = nroDocumento;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Long getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(Long nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

}
