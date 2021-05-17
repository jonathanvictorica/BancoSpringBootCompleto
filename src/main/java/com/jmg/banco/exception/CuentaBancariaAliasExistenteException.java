package com.jmg.banco.exception;

public class CuentaBancariaAliasExistenteException extends ServicioException {

    public CuentaBancariaAliasExistenteException(String alias) {
        super("La cuenta con alias" + alias + " no existe.");
    }

}
