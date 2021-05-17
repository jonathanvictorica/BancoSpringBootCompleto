package com.jmg.banco.exception;

public class NoExisteException extends ServicioException {

    public NoExisteException(Long id) {
        super("No existe el id: " + id);
    }
}
