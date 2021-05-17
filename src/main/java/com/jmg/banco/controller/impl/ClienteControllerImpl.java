package com.jmg.banco.controller.impl;

import com.jmg.banco.controller.ClienteController;
import com.jmg.banco.controller.dto.ClienteDTO;
import com.jmg.banco.controller.dto.ClienteResponseDTO;
import com.jmg.banco.domain.Cliente;
import com.jmg.banco.exception.ServicioException;
import com.jmg.banco.mapper.ClienteMapper;
import com.jmg.banco.service.AdmClienteUseCase;
import com.jmg.banco.service.ConsultarClienteUseCase;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Cliente", description = ("Administracion y Gestion de Clientes"))
public class ClienteControllerImpl implements ClienteController {

    private final AdmClienteUseCase admClienteUseCase;
    private final ConsultarClienteUseCase buscarClienteUseCase;

    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteControllerImpl(AdmClienteUseCase admClienteUseCase, ConsultarClienteUseCase buscarClienteUseCase) {
        super();
        this.admClienteUseCase = admClienteUseCase;
        this.buscarClienteUseCase = buscarClienteUseCase;
        this.clienteMapper = ClienteMapper.INSTANCE;
    }

    @Override
    @PostMapping(path = "/cliente/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ClienteDTO cliente) throws ServicioException {
        admClienteUseCase.crearCliente(clienteMapper.ClienteDtoToCliente(cliente));
    }

    @Override
    @PutMapping(path = "/cliente/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable(name = "id") Long id, @RequestBody ClienteDTO cliente) throws ServicioException {
        admClienteUseCase.modificarCliente(clienteMapper.ClienteDtoToCliente(cliente));
    }

    @Override
    @PatchMapping(path = "/cliente/updateNombreApellido/{id}/{nombre}/{apellido}")
    @ResponseStatus(HttpStatus.OK)
    public void updateNombreApellido(@PathVariable(name = "id") Long id, @PathVariable(name = "nombre") String nombre, @PathVariable(name = "apellido") String apellido) throws ServicioException {
        admClienteUseCase.modificarCliente(new Cliente(id, nombre, apellido));
    }

    @Override
    @DeleteMapping(path = "/cliente/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(name = "id") Long id) throws ServicioException {
        admClienteUseCase.eliminarCliente(id);
    }

    @Override
    @GetMapping(path = "/cliente/get/{id}")
    public ResponseEntity<ClienteResponseDTO> getById(@PathVariable(name = "id") Long id) throws ServicioException {
        Optional<Cliente> cliente = buscarClienteUseCase.buscarClientePorId(id);
        if (cliente.isPresent()) {
            return new ResponseEntity<ClienteResponseDTO>(clienteMapper.ClienteToClienteResponseDto(cliente.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<ClienteResponseDTO>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping(path = "/cliente/getByDocumento/{nroDocumento}")
    public ResponseEntity<ClienteResponseDTO> getByDocumento(@PathVariable(name = "nroDocumento") Long nroDocumento) throws ServicioException {
        Optional<Cliente> cliente = buscarClienteUseCase.buscarClientePorDocumento(nroDocumento);
        if (cliente.isPresent()) {
            return new ResponseEntity<ClienteResponseDTO>(clienteMapper.ClienteToClienteResponseDto(cliente.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<ClienteResponseDTO>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @GetMapping(path = "/cliente/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteResponseDTO> all() throws ServicioException {
        return buscarClienteUseCase.buscarTodosClientes().stream().map(c -> clienteMapper.ClienteToClienteResponseDto(c)).collect(Collectors.toList());
    }
}
