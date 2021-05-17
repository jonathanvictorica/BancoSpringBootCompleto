package com.jmg.banco.service;

import com.jmg.banco.domain.CuentaBancaria;
import com.jmg.banco.exception.ServicioException;

import java.math.BigDecimal;
import java.util.List;

public interface ConsultarCuentaBancariaUseCase {

    public List<CuentaBancaria> listarCuentasBancariasPorIdCliente(Long idCliente);

    public List<CuentaBancaria> listarCuentasBancariasPorTipoYNumeroDocumentoCliente(String tipoDocumento, String numeroDocumento);

    public CuentaBancaria obtenerCuentaBancariaPorId(Long idCuentaBancaria) throws ServicioException;

    public BigDecimal obtenerSaldoCuenta(Long idCuentaBancaria) throws ServicioException;

    public Boolean verificarSobregiro(Long idCuentaBancaria) throws ServicioException;
}
