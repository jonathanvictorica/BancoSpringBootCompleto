package com.jmg.banco.repository.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmg.banco.domain.CuentaBancaria;
import com.jmg.banco.domain.EstadoCuenta;
import com.jmg.banco.repository.CuentaBancariaRepositoryPort;
import com.jmg.banco.repository.hibernate.CuentaBancariaHibernateRepository;

@Component
public class CuentaBancariaRepositoryAdapter implements CuentaBancariaRepositoryPort {

	private CuentaBancariaHibernateRepository cuentaBancariaRepository;

	@Autowired
	public CuentaBancariaRepositoryAdapter(CuentaBancariaHibernateRepository cuentaBancariaRepository) {
		super();
		this.cuentaBancariaRepository = cuentaBancariaRepository;
	}

	@Override
	public Optional<CuentaBancaria> buscarCuentaAbiertaPorId(Long id) {
		return cuentaBancariaRepository.findById(id);
	}

	@Override
	public void modificarSaldo(BigDecimal saldoCuenta, Date fechaActualizacion, Long id) {
		cuentaBancariaRepository.setSaldoAndFechaSaldoById(saldoCuenta, fechaActualizacion, id);

	}

	@Override
	public void crearCuentaBancaria(CuentaBancaria cuenta) {
		cuentaBancariaRepository.save(cuenta);

	}

	@Override
	public boolean existePorAlias(String alias) {
		return cuentaBancariaRepository.existsByAliasAndEstadoCuenta(alias, EstadoCuenta.Activa.name());
	}

	@Override
	public boolean existePorId(Long idCuentaBancaria) {
		return cuentaBancariaRepository.existsByIdAndEstadoCuenta(idCuentaBancaria, EstadoCuenta.Activa.name());
	}

	@Override
	public void modificarAlias(String aliasNuevo, Long idCuentaBancaria) {
		cuentaBancariaRepository.setAliasById(aliasNuevo, idCuentaBancaria);

	}

	@Override
	public void eliminarCuentaBancaria(Long idCuentaBancaria) {
		cuentaBancariaRepository.deleteById(idCuentaBancaria);

	}

	@Override
	public List<CuentaBancaria> listarCuentasBancariasActivasPorIdCliente(Long idCliente) {
		return cuentaBancariaRepository.findByIdClienteByEstado(idCliente, EstadoCuenta.Activa.name());
	}

	@Override
	public List<CuentaBancaria> listarCuentasBancariasPorTipoYNumeroDocumentoCliente(String tipoDocumento, String numeroDocumento) {
		return cuentaBancariaRepository.findAllByDocumentoClienteByEstado(tipoDocumento, numeroDocumento, EstadoCuenta.Activa.name());
	}

	@Override
	public BigDecimal obtenerSaldoCuenta(Long idCuentaBancaria) {
		return cuentaBancariaRepository.findSaldoCuentaByIdAndEstado(idCuentaBancaria, EstadoCuenta.Activa.name());
	}

}
