package com.jmg.banco.controller.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CuentaBancariaResponseDTO {

    private Long id;

    private Long idCliente;

    private String tipoDocumento;

    private String numeroDocumento;

    private Date fechaActualizacionSaldo;

    private BigDecimal saldoCuenta;

    private String alias;

    private String estadoCuenta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
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


}
