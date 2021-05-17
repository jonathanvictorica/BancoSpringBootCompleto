package com.jmg.banco.service;

import com.jmg.banco.domain.Cliente;
import com.jmg.banco.exception.ServicioException;

public interface AdmClienteUseCase {

    public void crearCliente(Cliente cliente);

    public void modificarCliente(Cliente cliente) throws ServicioException;

    public void eliminarCliente(Long id);
}
