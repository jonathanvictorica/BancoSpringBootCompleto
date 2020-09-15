package com.jmg.banco.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "BC0002_CuentaBancaria", indexes = { @Index(name = "IDX_cuentabancaria_idcliente", columnList = "fk_cliente", unique = false) })
@Access(value = AccessType.FIELD)
public class CuentaBancaria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonProperty("id")
	private Long id;

	@JoinColumn(name = "fk_cliente", nullable = false, referencedColumnName = "id_cliente")
	/*
	 * FetchType.LAZY = Esto no carga las relaciones a menos que lo invoque a través
	 * del método getter.
	 * 
	 * FetchType.EAGER = Esto carga todas las relaciones.
	 * 
	 * Pros y contras de estos dos tipos de búsqueda.
	 * 
	 * Lazy initialization mejora el rendimiento al evitar cálculos innecesarios y
	 * reduce los requisitos de memoria.
	 * 
	 * Eager initialization consume más memoria y la velocidad de procesamiento es
	 * lenta.
	 */
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Cliente cliente;

	@Column(name = "fecha_actualizacion_saldo", nullable = false)
	private Date fechaActualizacionSaldo;

	// decimal(18,2)
	@Column(name = "saldo_cuenta", precision = 18, scale = 2, nullable = false)
	private BigDecimal saldoCuenta;

	@Column(name = "alias", length = 20, nullable = false)
	@Size(min = 5, max = 20)
	private String alias;

	@Column(name = "estado_cuenta", length = 20, nullable = false)
	@Size(min = 1, max = 20)
	private String estadoCuenta;

	@Column(name = "fecha_hora_apertura", nullable = false)
	private Date fechaHoraApertura;

	public CuentaBancaria() {
		super();
	}

	public CuentaBancaria(Cliente cliente, Date fechaActualizacionSaldo, BigDecimal saldoCuenta, @Size(min = 5, max = 20) String alias, @Size(min = 1, max = 20) String estadoCuenta, Date fechaHoraApertura) {
		super();
		this.cliente = cliente;
		this.fechaActualizacionSaldo = fechaActualizacionSaldo;
		this.saldoCuenta = saldoCuenta;
		this.alias = alias;
		this.estadoCuenta = estadoCuenta;
		this.fechaHoraApertura = fechaHoraApertura;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFechaActualizacionSaldo() {
		return fechaActualizacionSaldo;
	}

	public void setFechaActualizacionSaldo(Date fechaActualizacionSaldo) {
		this.fechaActualizacionSaldo = fechaActualizacionSaldo;
	}

	public BigDecimal getSaldoCuenta() {
		return saldoCuenta;
	}

	public void setSaldoCuenta(BigDecimal saldoCuenta) {
		this.saldoCuenta = saldoCuenta;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(String estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	public Date getFechaHoraApertura() {
		return fechaHoraApertura;
	}

	public void setFechaHoraApertura(Date fechaHoraApertura) {
		this.fechaHoraApertura = fechaHoraApertura;
	}

	public boolean tieneSaldoDisponiblePor(BigDecimal importe) {
		return this.saldoCuenta.compareTo(importe) >= 1;
	}

	public void debitarSaldo(BigDecimal importe) {
		this.saldoCuenta = this.saldoCuenta.subtract(importe);
	}

	public void acreditarSaldo(BigDecimal importe) {
		this.saldoCuenta = this.saldoCuenta.add(importe);
	}

	public BigDecimal obtenerNuevoSaldoDebitando(BigDecimal importe) {
		return this.saldoCuenta.subtract(importe);
	}

	public boolean estaSobregirada() {
		return this.saldoCuenta.compareTo(BigDecimal.ZERO) < 1;
	}

}
