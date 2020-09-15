package com.jmg.banco.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.jmg.banco.controller.dto.ClienteDTO;
import com.jmg.banco.controller.dto.ClienteResponseDTO;
import com.jmg.banco.exception.ServicioException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

public interface ClienteController {

	@ApiOperation(value = "Crear Cliente", notes = "Tener en cuenta tal validacion")
	void create(@ApiParam(value = "Datos Cliente") ClienteDTO cliente) throws ServicioException;

	void update(Long id, ClienteDTO cliente) throws ServicioException;

	void updateNombreApellido(Long id, String nombre, String apellido) throws ServicioException;

	void deleteById(Long id) throws ServicioException;

	ResponseEntity<ClienteResponseDTO> getById(Long id) throws ServicioException;

	ResponseEntity<ClienteResponseDTO> getByDocumento(Long nroDocumento) throws ServicioException;

	List<ClienteResponseDTO> all() throws ServicioException;

}