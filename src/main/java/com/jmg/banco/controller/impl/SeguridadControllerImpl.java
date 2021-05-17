package com.jmg.banco.controller.impl;

import com.jmg.banco.controller.SeguridadController;
import com.jmg.banco.exception.ServicioException;
import com.jmg.banco.service.AccionesAutenticarUseCase;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Seguridad", description = "Gestión de Seguridad para Acceso al Servicio")
public class SeguridadControllerImpl implements SeguridadController {

    private AccionesAutenticarUseCase accionesCuentaBancariaUseCase;

    @Autowired
    public SeguridadControllerImpl(AccionesAutenticarUseCase accionesCuentaBancariaUseCase) {
        super();
        this.accionesCuentaBancariaUseCase = accionesCuentaBancariaUseCase;
    }

    @PostMapping(path = "/seguridad/autenticar/{usuario}/{password}")
    public String autenticar(@PathVariable(name = "usuario") String usuario, @PathVariable(name = "password") String password) throws ServicioException {
        return accionesCuentaBancariaUseCase.generarTokenAutenticacion(usuario, password);
    }
}
