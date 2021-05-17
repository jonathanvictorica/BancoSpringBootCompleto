package com.jmg.banco.exception;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;

@Getter
@Setter
@Access(value = AccessType.FIELD)
public class ServicioException extends Exception {

    private String mensaje;

    public ServicioException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

}
