package com.jmg.banco.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class CuentaBancariaDTO {

    @NotNull
    private Long idCliente;

    @NotNull
    private BigDecimal saldoCuenta;

    @Size(min = 2, max = 10)
    @NotNull
    private String alias;

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
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

}
