package com.jmg.banco.service;

import com.jmg.banco.domain.Cliente;

import java.util.List;
import java.util.Optional;

public interface ConsultarClienteUseCase {

    Optional<Cliente> buscarClientePorId(Long id);

    List<Cliente> buscarTodosClientes();

    Optional<Cliente> buscarClientePorDocumento(Long nroDocumento);

}
