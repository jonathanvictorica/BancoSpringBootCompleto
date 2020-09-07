package com.jmg.banco.security;

import java.util.List;

public class UsuarioSecurity {

	private Long id;
	private String nombre;
	private String password;
	private List<RolesSecurity> roles;

	public UsuarioSecurity(Long id, String nombre, String password, List<RolesSecurity> roles) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RolesSecurity> getRoles() {
		return roles;
	}

	public void setRoles(List<RolesSecurity> roles) {
		this.roles = roles;
	}

}
