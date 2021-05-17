package com.jmg.banco.mapper;

import com.jmg.banco.controller.dto.CuentaBancariaDTO;
import com.jmg.banco.controller.dto.CuentaBancariaResponseDTO;
import com.jmg.banco.domain.CuentaBancaria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CuentaBancariaMapper {
    CuentaBancariaMapper INSTANCE = Mappers.getMapper(CuentaBancariaMapper.class);

    @Mappings({@Mapping(target = "cliente.id", source = "idCliente")})
    CuentaBancaria cuentaBancariaDtoToCuentaBancaria(CuentaBancariaDTO unit);

    @Mappings({@Mapping(target = "idCliente", source = "cliente.id"), @Mapping(target = "tipoDocumento", source = "cliente.tipoDocumento"), @Mapping(target = "numeroDocumento", source = "cliente.nroDocumento")})
    CuentaBancariaResponseDTO cuentaBancariaDtoToCuentaBancaria(CuentaBancaria unit);

}
