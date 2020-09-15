package com.jmg.banco.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.jmg.banco.domain.Cliente;
import com.jmg.banco.domain.CuentaBancaria;
import com.jmg.banco.domain.EstadoCuenta;
import com.jmg.banco.exception.ClienteNoExiste;
import com.jmg.banco.exception.CuentaBancariaAliasExistenteException;
import com.jmg.banco.exception.CuentaBancariaNoExisteException;
import com.jmg.banco.exception.CuentaBancariaSinSaldoDisponibleException;
import com.jmg.banco.exception.CuentaBancariaSobreGiradaException;
import com.jmg.banco.exception.ServicioException;
import com.jmg.banco.repository.port.ClienteRepositoryPort;
import com.jmg.banco.repository.port.CuentaBancariaRepositoryPort;
import com.jmg.banco.service.AccionesCuentaBancariaUseCase;
import com.jmg.banco.service.AdmCuentaBancariaUseCase;
import com.jmg.banco.service.ConsultarCuentaBancariaUseCase;
import com.jmg.banco.ws.port.ConsultaDeudaOtroBancoPort;

@Component
public class CuentaBancariaService implements AdmCuentaBancariaUseCase, AccionesCuentaBancariaUseCase, ConsultarCuentaBancariaUseCase {

	private ClienteRepositoryPort clienteRepositoryPort;
	private CuentaBancariaRepositoryPort cuentaBancariaRepositoryPort;
	private ConsultaDeudaOtroBancoPort consultaDeudaOtroBancoPort;

	public CuentaBancariaService(ClienteRepositoryPort clienteRepositoryPort, CuentaBancariaRepositoryPort cuentaBancariaRepositoryPort, ConsultaDeudaOtroBancoPort consultaDeudaOtroBancoPort) {
		super();
		this.clienteRepositoryPort = clienteRepositoryPort;
		this.cuentaBancariaRepositoryPort = cuentaBancariaRepositoryPort;
		this.consultaDeudaOtroBancoPort = consultaDeudaOtroBancoPort;
	}

	@Override
	@Transactional // se le agrega esto porque necesitamos la operacion atomica
	public void debitar(Long idCuentaBancariaDebitar, Long idCuentaBancariaDestino, BigDecimal importe) throws ServicioException {
		Optional<CuentaBancaria> cuentaOrigen = cuentaBancariaRepositoryPort.buscarCuentaAbiertaPorId(idCuentaBancariaDebitar);
		if (!cuentaOrigen.isPresent()) {
			throw new CuentaBancariaNoExisteException(idCuentaBancariaDebitar);
		}
		Optional<CuentaBancaria> cuentaDestino = cuentaBancariaRepositoryPort.buscarCuentaAbiertaPorId(idCuentaBancariaDestino);
		if (!cuentaDestino.isPresent()) {
			throw new CuentaBancariaNoExisteException(idCuentaBancariaDebitar);
		}

		if (!cuentaOrigen.get().tieneSaldoDisponiblePor(importe)) {
			throw new CuentaBancariaSinSaldoDisponibleException(idCuentaBancariaDebitar, importe);
		}

		cuentaOrigen.get().debitarSaldo(importe);
		cuentaDestino.get().acreditarSaldo(importe);

		cuentaBancariaRepositoryPort.modificarSaldo(cuentaOrigen.get().getSaldoCuenta(), new Date(), cuentaOrigen.get().getId());
		cuentaBancariaRepositoryPort.modificarSaldo(cuentaDestino.get().getSaldoCuenta(), new Date(), cuentaDestino.get().getId());

	}

	@Override
	@Transactional
	public void debitarConTopeSobregiro(Long idCuentaBancariaDebitar, Long idCuentaBancariaDestino, BigDecimal importe, String autorizanteSobregiro, BigDecimal importeMaximoSobregirarCuentaDebitar) throws ServicioException {
		Optional<CuentaBancaria> cuentaOrigen = cuentaBancariaRepositoryPort.buscarCuentaAbiertaPorId(idCuentaBancariaDebitar);
		if (!cuentaOrigen.isPresent()) {
			throw new CuentaBancariaNoExisteException(idCuentaBancariaDebitar);
		}
		Optional<CuentaBancaria> cuentaDestino = cuentaBancariaRepositoryPort.buscarCuentaAbiertaPorId(idCuentaBancariaDestino);
		if (!cuentaDestino.isPresent()) {
			throw new CuentaBancariaNoExisteException(idCuentaBancariaDebitar);
		}

		if (cuentaOrigen.get().obtenerNuevoSaldoDebitando(importe).abs().compareTo(importeMaximoSobregirarCuentaDebitar.abs()) <= 1) {
			throw new CuentaBancariaSinSaldoDisponibleException(idCuentaBancariaDebitar, importe);
		}

		cuentaOrigen.get().debitarSaldo(importe);
		cuentaDestino.get().acreditarSaldo(importe);

		cuentaBancariaRepositoryPort.modificarSaldo(cuentaOrigen.get().getSaldoCuenta(), new Date(), cuentaOrigen.get().getId());
		cuentaBancariaRepositoryPort.modificarSaldo(cuentaDestino.get().getSaldoCuenta(), new Date(), cuentaDestino.get().getId());

	}

	@Override
	public void crearCuentaBancaria(CuentaBancaria cuenta) throws ServicioException {
		if (cuentaBancariaRepositoryPort.existePorAlias(cuenta.getAlias())) {
			throw new CuentaBancariaAliasExistenteException(cuenta.getAlias());
		}

		Optional<Cliente> cliente = clienteRepositoryPort.buscarClientePorId(cuenta.getCliente().getId());
		if (!cliente.isPresent()) {
			throw new ClienteNoExiste(cuenta.getCliente().getId());
		}

		cuenta.setFechaActualizacionSaldo(new Date());
		cuenta.setFechaHoraApertura(new Date());
		cuenta.setEstadoCuenta(EstadoCuenta.Activa.name());
		cuenta.setCliente(cliente.get());
		cuentaBancariaRepositoryPort.crearCuentaBancaria(cuenta);

	}

	@Override
	public void modificarAlias(Long idCuentaBancaria, String aliasNuevo) throws ServicioException {
		if (cuentaBancariaRepositoryPort.existePorId(idCuentaBancaria)) {
			throw new CuentaBancariaNoExisteException(idCuentaBancaria);
		}
		if (cuentaBancariaRepositoryPort.existePorAlias(aliasNuevo)) {
			throw new CuentaBancariaAliasExistenteException(aliasNuevo);
		}
		cuentaBancariaRepositoryPort.modificarAlias(aliasNuevo, idCuentaBancaria);
	}

	@Override
	public void eliminarCuentaBancaria(Long idCuentaBancaria) throws ServicioException {
		Optional<CuentaBancaria> cuenta = cuentaBancariaRepositoryPort.buscarCuentaAbiertaPorId(idCuentaBancaria);
		if (!cuenta.isPresent()) {
			throw new CuentaBancariaNoExisteException(idCuentaBancaria);
		}

		if (cuenta.get().estaSobregirada()) {
			throw new CuentaBancariaSobreGiradaException(cuenta.get().getAlias());
		}

		cuentaBancariaRepositoryPort.eliminarCuentaBancaria(idCuentaBancaria);

	}

	@Override
	public List<CuentaBancaria> listarCuentasBancariasPorIdCliente(Long idCliente) {
		return cuentaBancariaRepositoryPort.listarCuentasBancariasActivasPorIdCliente(idCliente);
	}

	@Override
	public List<CuentaBancaria> listarCuentasBancariasPorTipoYNumeroDocumentoCliente(String tipoDocumento, String numeroDocumento) {
		return cuentaBancariaRepositoryPort.listarCuentasBancariasPorTipoYNumeroDocumentoCliente(tipoDocumento, numeroDocumento);
	}

	@Override
	public CuentaBancaria obtenerCuentaBancariaPorId(Long idCuentaBancaria) throws ServicioException {
		Optional<CuentaBancaria> cuentaOrigen = cuentaBancariaRepositoryPort.buscarCuentaAbiertaPorId(idCuentaBancaria);
		if (!cuentaOrigen.isPresent()) {
			throw new CuentaBancariaNoExisteException(idCuentaBancaria);
		}
		return cuentaOrigen.get();
	}

	@Override
	public BigDecimal obtenerSaldoCuenta(Long idCuentaBancaria) throws ServicioException {
		if (!cuentaBancariaRepositoryPort.existePorId(idCuentaBancaria)) {
			throw new CuentaBancariaNoExisteException(idCuentaBancaria);
		}
		return cuentaBancariaRepositoryPort.obtenerSaldoCuenta(idCuentaBancaria);
	}

	@Override
	public Boolean verificarSobregiro(Long idCuentaBancaria) throws ServicioException {
		if (!cuentaBancariaRepositoryPort.existePorId(idCuentaBancaria)) {
			throw new CuentaBancariaNoExisteException(idCuentaBancaria);
		}
		return cuentaBancariaRepositoryPort.obtenerSaldoCuenta(idCuentaBancaria).compareTo(BigDecimal.ZERO) < 1;
	}

}
