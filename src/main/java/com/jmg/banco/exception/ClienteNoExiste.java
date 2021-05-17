package com.jmg.banco.exception;

public class ClienteNoExiste extends ServicioException {

    public ClienteNoExiste(Long id) {
        super("El cliente con id " + id + " no existe.");
    }

}
