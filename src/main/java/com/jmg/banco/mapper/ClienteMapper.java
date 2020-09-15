package com.jmg.banco.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.jmg.banco.controller.dto.ClienteDTO;
import com.jmg.banco.controller.dto.ClienteResponseDTO;
import com.jmg.banco.domain.Cliente;

@Mapper
public interface ClienteMapper {
	ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

	
	Cliente ClienteDtoToCliente(ClienteDTO unit);
	
	
	ClienteResponseDTO ClienteToClienteResponseDto(Cliente unit);
}
