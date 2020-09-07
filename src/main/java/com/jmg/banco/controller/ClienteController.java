package com.jmg.banco.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.jmg.banco.domain.Cliente;
import com.jmg.banco.exception.ServicioException;
import com.jmg.banco.service.AdmClienteUseCase;
import com.jmg.banco.service.ConsultarClienteUseCase;

@RestController
public class ClienteController  {

	private AdmClienteUseCase admClienteUseCase;
	private ConsultarClienteUseCase buscarClienteUseCase;

	@Autowired
	public ClienteController(AdmClienteUseCase admClienteUseCase, ConsultarClienteUseCase buscarClienteUseCase) {
		super();
		this.admClienteUseCase = admClienteUseCase;
		this.buscarClienteUseCase = buscarClienteUseCase;
	}

	@PostMapping(path = "/cliente/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Validated(Cliente.AddValidations.class) @RequestBody Cliente cliente) throws ServicioException {
		admClienteUseCase.crearCliente(cliente);
	}

	@PutMapping(path = "/cliente/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable(name = "id") Long id, @Validated(Cliente.UpdateValidations.class) @RequestBody Cliente cliente) throws ServicioException {
		admClienteUseCase.modificarCliente(cliente);
	}

	@PatchMapping(path = "/cliente/updateNombreApellido/{id}/{nombre}/{apellido}")
	@ResponseStatus(HttpStatus.OK)
	public void updateNombreApellido(@PathVariable(name = "id") Long id, @PathVariable(name = "nombre") String nombre, @PathVariable(name = "apellido") String apellido) throws ServicioException {
		admClienteUseCase.modificarCliente(new Cliente(id, nombre, apellido));
	}

	@DeleteMapping(path = "/cliente/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteById(@PathVariable(name = "id") Long id) throws ServicioException {
		admClienteUseCase.eliminarCliente(id);
	}

	@GetMapping(path = "/cliente/get/{id}")
	public ResponseEntity<Cliente> getById(@PathVariable(name = "id") Long id) throws ServicioException {
		Optional<Cliente> cliente = buscarClienteUseCase.buscarClientePorId(id);
		if (cliente.isPresent()) {
			return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/cliente/getByDocumento/{nroDocumento}")
	public ResponseEntity<Cliente> getByDocumento(@PathVariable(name = "nroDocumento") Long nroDocumento) throws ServicioException {
		Optional<Cliente> cliente = buscarClienteUseCase.buscarClientePorDocumento(nroDocumento);
		if (cliente.isPresent()) {
			return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/cliente/all")
	@ResponseStatus(HttpStatus.OK)
	public List<Cliente> all() throws ServicioException {
		return buscarClienteUseCase.buscarTodosClientes();
	}
}
