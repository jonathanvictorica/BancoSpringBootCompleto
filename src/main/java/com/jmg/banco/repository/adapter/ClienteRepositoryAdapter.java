package com.jmg.banco.repository.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmg.banco.domain.Cliente;
import com.jmg.banco.exception.NoExisteException;
import com.jmg.banco.exception.ServicioException;
import com.jmg.banco.repository.hibernate.ClienteHibernateRepository;
import com.jmg.banco.repository.port.ClienteRepositoryPort;

@Component
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

	private ClienteHibernateRepository clienteRepository;

	@Autowired
	public ClienteRepositoryAdapter(ClienteHibernateRepository clienteRepository) {
		super();
		this.clienteRepository = clienteRepository;
	}

	@Override
	public void crearCliente(Cliente cliente) {
		clienteRepository.save(cliente);
	}

	@Override
	public Optional<Cliente> buscarClientePorId(Long id) {
		return clienteRepository.findById(id);

	}

	@Override
	public List<Cliente> buscarTodosClientes() {
		return (List<Cliente>) clienteRepository.findAll();
	}

	@Override
	public void modificarCliente(Cliente cliente) throws ServicioException {
		if (clienteRepository.existsById(cliente.getId())) {
			clienteRepository.save(cliente);
		} else {
			throw new NoExisteException(cliente.getId());
		}
	}

	@Override
	public void eliminarCliente(Long id) {
		clienteRepository.deleteById(id);
	}

	@Override
	public Optional<Cliente> buscarClientePorDocumento(Long nroDocumento) {
		return clienteRepository.findByNroDocumento(nroDocumento);
	}

}
