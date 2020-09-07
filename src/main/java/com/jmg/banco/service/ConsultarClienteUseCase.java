package com.jmg.banco.service;

import java.util.List;
import java.util.Optional;

import com.jmg.banco.domain.Cliente;

public interface ConsultarClienteUseCase {

	Optional<Cliente> buscarClientePorId(Long id);

	List<Cliente> buscarTodosClientes();

	Optional<Cliente> buscarClientePorDocumento(Long nroDocumento);

}
