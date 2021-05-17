package com.jmg.banco.ws.port;

import com.jmg.banco.ws.dto.DeudaClienteResponseDTO;

public interface ConsultaDeudaOtroBancoPort {

    public DeudaClienteResponseDTO obtenerDeudaCliente(String tipo, String documento);
}
