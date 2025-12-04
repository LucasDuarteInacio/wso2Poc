package com.challenge.OrderProcessingManagement.controller;

import com.challenge.OrderProcessingManagement.api.ProdutosApi;
import com.challenge.OrderProcessingManagement.api.model.Produto;
import com.challenge.OrderProcessingManagement.api.model.ProdutoInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProdutosControllerImpl implements ProdutosApi {

    @Override
    public ResponseEntity<List<Produto>> listarProdutos() {
        System.out.println("Listando Produtos");
        // TODO: Implementar lógica para listar todos os produtos
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Produto> criarProduto(ProdutoInput produtoInput) {
        // TODO: Implementar lógica para criar um novo produto
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Produto> buscarProdutoPorId(Integer id) {
        // TODO: Implementar lógica para buscar produto por ID
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Produto> atualizarProduto(Integer id, ProdutoInput produtoInput) {
        // TODO: Implementar lógica para atualizar produto
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> excluirProduto(Integer id) {
        // TODO: Implementar lógica para excluir produto
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
