package com.challenge.OrderProcessingManagement.controller;

import com.challenge.OrderProcessingManagement.api.ClientesApi;
import com.challenge.OrderProcessingManagement.api.model.Cliente;
import com.challenge.OrderProcessingManagement.api.model.ClienteInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ClientesControllerImpl implements ClientesApi {

    @Override
    public ResponseEntity<List<Cliente>> listarClientes() {
        System.out.println("Listando Clientes");
        // TODO: Implementar lógica para listar todos os clientes
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Cliente> criarCliente(ClienteInput clienteInput) {
        System.out.println("Criando Cliente: " + clienteInput.getNome());
        // TODO: Implementar lógica para criar um novo cliente
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Cliente> buscarClientePorId(Integer id) {
        System.out.println("Buscando Cliente por ID: " + id);
        // TODO: Implementar lógica para buscar cliente por ID
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
