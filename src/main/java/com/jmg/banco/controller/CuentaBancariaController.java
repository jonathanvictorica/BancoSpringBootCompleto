package com.jmg.banco.controller;

import com.jmg.banco.controller.dto.CuentaBancariaDTO;
import com.jmg.banco.controller.dto.CuentaBancariaResponseDTO;
import com.jmg.banco.exception.ServicioException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaBancariaController {

    @ApiOperation("Operación para crear cuenta bancaria de un cliente.")
    void create(@ApiParam("cuenta Bancaria") CuentaBancariaDTO cuentaBancaria) throws ServicioException;

    void updateAlias(Long id, String aliasNuevo) throws ServicioException;

    void delete(Long id) throws ServicioException;

    void debit(Long idCuentaBancariaDebitar, Long idCuentaBancariaDestino, BigDecimal importe) throws ServicioException;

    void debitAutoritation(Long idCuentaBancariaDebitar, Long idCuentaBancariaDestino, BigDecimal importe, String autorizante, BigDecimal importeAutorizadoSobregiro) throws ServicioException;

    List<CuentaBancariaResponseDTO> getByIdCliente(Long idCliente);

    List<CuentaBancariaResponseDTO> getByDocumentoCliente(String tipoDocumento, String numeroDocumento);

    CuentaBancariaResponseDTO getByIdCuentaBancaria(Long idCuentaBancaria) throws ServicioException;

    BigDecimal getSaldoByIdCuentaBancaria(Long idCuentaBancaria) throws ServicioException;

}