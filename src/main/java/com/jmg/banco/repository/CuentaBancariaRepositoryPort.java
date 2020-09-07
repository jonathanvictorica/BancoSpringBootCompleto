package com.jmg.banco.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.jmg.banco.domain.CuentaBancaria;

public interface CuentaBancariaRepositoryPort {

	Optional<CuentaBancaria> buscarCuentaAbiertaPorId(Long id);

	void modificarSaldo(BigDecimal saldoCuenta, Date fechaActualizacion, Long id);

	void crearCuentaBancaria(CuentaBancaria cuenta);

	boolean existePorAlias(String alias);

	boolean existePorId(Long idCuentaBancaria);

	void modificarAlias(String aliasNuevo, Long idCuentaBancaria);

	void eliminarCuentaBancaria(Long idCuentaBancaria);

	List<CuentaBancaria> listarCuentasBancariasActivasPorIdCliente(Long idCliente);

	List<CuentaBancaria> listarCuentasBancariasPorTipoYNumeroDocumentoCliente(String tipoDocumento, String numeroDocumento);

	BigDecimal obtenerSaldoCuenta(Long idCuentaBancaria);

}
