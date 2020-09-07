package com.jmg.banco.service;

import java.math.BigDecimal;

import com.jmg.banco.exception.ServicioException;

public interface AccionesCuentaBancariaUseCase {

	public void debitar(Long idCuentaBancariaDebitar, Long idCuentaBancariaDestino, BigDecimal importe)throws ServicioException;

	public void debitarConTopeSobregiro(Long idCuentaBancariaDebitar, Long idCuentaBancariaDestino, BigDecimal importe, String autorizanteSobregiro, BigDecimal importeMaximoSobregirarCuentaDebitar)throws ServicioException;

}
