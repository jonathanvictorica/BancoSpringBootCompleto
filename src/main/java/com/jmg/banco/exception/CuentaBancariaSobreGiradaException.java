package com.jmg.banco.exception;

public class CuentaBancariaSobreGiradaException extends ServicioException {

    public CuentaBancariaSobreGiradaException(String alias) {
        super("La cuenta con alias: " + alias + " se encuentra sobregirada. Para realizar un débito debe ser por debito con sobregiro autorizado.");
    }

}
