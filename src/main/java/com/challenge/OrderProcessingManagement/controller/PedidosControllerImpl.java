package com.challenge.OrderProcessingManagement.controller;

import com.challenge.OrderProcessingManagement.api.PedidosApi;
import com.challenge.OrderProcessingManagement.api.model.AtualizarStatusPedido;
import com.challenge.OrderProcessingManagement.api.model.Pedido;
import com.challenge.OrderProcessingManagement.api.model.PedidoInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PedidosControllerImpl implements PedidosApi {

    @Override
    public ResponseEntity<List<Pedido>> listarPedidos() {
        System.out.println("Listando Pedidos");
        // TODO: Implementar lógica para listar todos os pedidos
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Pedido> criarPedido(PedidoInput pedidoInput) {
        System.out.println("Criando Pedido para Cliente ID: " + pedidoInput.getClienteId());
        // TODO: Implementar lógica para criar um novo pedido
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Pedido> buscarPedidoPorId(Integer id) {
        System.out.println("Buscando Pedido por ID: " + id);
        // TODO: Implementar lógica para buscar pedido por ID
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<List<Pedido>> listarPedidosPorCliente(Integer clienteId) {
        System.out.println("Listando Pedidos do Cliente ID: " + clienteId);
        // TODO: Implementar lógica para listar pedidos por cliente
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Pedido> atualizarStatusPedido(Integer id, AtualizarStatusPedido atualizarStatusPedido) {
        System.out.println("Atualizando Status do Pedido ID: " + id + " para: " + atualizarStatusPedido.getStatus());
        // TODO: Implementar lógica para atualizar status do pedido
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
