package com.jmg.banco.repository.port;

import java.util.List;
import java.util.Optional;

import com.jmg.banco.domain.Cliente;
import com.jmg.banco.exception.ServicioException;

public interface ClienteRepositoryPort {

	void crearCliente(Cliente cliente);

	Optional<Cliente> buscarClientePorId(Long id);

	List<Cliente> buscarTodosClientes();

	void modificarCliente(Cliente cliente) throws ServicioException;

	void eliminarCliente(Long id);

	Optional<Cliente> buscarClientePorDocumento(Long nroDocumento);

}
