package com.jmg.banco.ws.adapter;

import com.jmg.banco.ws.dto.DeudaClienteResponseDTO;
import com.jmg.banco.ws.port.ConsultaDeudaOtroBancoPort;
import org.springframework.stereotype.Component;

@Component
public class ConsultaDeudaOtroBancoAdapter extends GenericWS implements ConsultaDeudaOtroBancoPort {

    @Override
    public DeudaClienteResponseDTO obtenerDeudaCliente(String tipo, String documento) {
        // TODO Auto-generated method stub
        return null;
    }

}
