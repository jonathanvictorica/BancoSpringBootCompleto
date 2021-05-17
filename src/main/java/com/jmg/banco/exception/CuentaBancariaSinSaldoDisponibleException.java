package com.jmg.banco.exception;

import java.math.BigDecimal;

public class CuentaBancariaSinSaldoDisponibleException extends ServicioException {

    public CuentaBancariaSinSaldoDisponibleException(Long idCuentaBancariaDebitar, BigDecimal importe) {
        super("La cuenta " + idCuentaBancariaDebitar + " no tiene saldo suficiente para realizar la operación de débito. Saldo actual: " + importe);
    }

}
