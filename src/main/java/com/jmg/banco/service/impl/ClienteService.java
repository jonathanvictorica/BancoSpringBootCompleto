package com.jmg.banco.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmg.banco.domain.Cliente;
import com.jmg.banco.exception.ServicioException;
import com.jmg.banco.repository.ClienteRepositoryPort;
import com.jmg.banco.service.AdmClienteUseCase;
import com.jmg.banco.service.ConsultarClienteUseCase;

@Component
public class ClienteService implements AdmClienteUseCase, ConsultarClienteUseCase {

	private ClienteRepositoryPort clienteRepository;

	@Autowired
	public ClienteService(ClienteRepositoryPort clienteRepository) {
		super();
		this.clienteRepository = clienteRepository;
	}

	@Override
	public void crearCliente(Cliente cliente) {
		clienteRepository.crearCliente(cliente);

	}

	@Override
	public Optional<Cliente> buscarClientePorId(Long id) {
		return clienteRepository.buscarClientePorId(id);
	}

	@Override
	public List<Cliente> buscarTodosClientes() {
		return clienteRepository.buscarTodosClientes();
	}

	@Override
	public void modificarCliente(Cliente cliente) throws ServicioException {
		clienteRepository.modificarCliente(cliente);

	}

	@Override
	public void eliminarCliente(Long id) {
		clienteRepository.eliminarCliente(id);

	}

	@Override
	public Optional<Cliente> buscarClientePorDocumento(Long nroDocumento) {
		return clienteRepository.buscarClientePorDocumento(nroDocumento);
	}

}
