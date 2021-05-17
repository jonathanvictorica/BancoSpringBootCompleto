package com.jmg.banco.service;

import com.jmg.banco.domain.CuentaBancaria;
import com.jmg.banco.exception.ServicioException;

public interface AdmCuentaBancariaUseCase {

    public void crearCuentaBancaria(CuentaBancaria cuenta) throws ServicioException;

    public void modificarAlias(Long idCuentaBancaria, String aliasNuevo) throws ServicioException;

    public void eliminarCuentaBancaria(Long idCuentaBancaria) throws ServicioException;

}
