package com.jmg.banco.exception;

public class CuentaBancariaNoExisteException extends ServicioException {

    public CuentaBancariaNoExisteException(Long idCuentaBancariaDebitar) {
        super("La cuenta " + idCuentaBancariaDebitar + " no existe.");
    }

}
