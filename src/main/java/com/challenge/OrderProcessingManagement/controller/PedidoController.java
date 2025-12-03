package com.challenge.OrderProcessingManagement.controller;

import com.challenge.OrderProcessingManagement.dto.AtualizarStatusPedidoDTO;
import com.challenge.OrderProcessingManagement.dto.PedidoDTO;
import com.challenge.OrderProcessingManagement.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @Operation(summary = "Criar novo pedido", description = "Cria um novo pedido com itens. O estoque dos produtos é atualizado automaticamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou estoque insuficiente"),
            @ApiResponse(responseCode = "404", description = "Cliente ou produto não encontrado")
    })
    public ResponseEntity<PedidoDTO> criar(@Valid @RequestBody PedidoDTO dto) {
        PedidoDTO pedido = pedidoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna os dados de um pedido específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<PedidoDTO> buscarPorId(
            @Parameter(description = "ID do pedido", required = true)
            @PathVariable Long id) {
        PedidoDTO pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista com todos os pedidos do sistema")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        List<PedidoDTO> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar pedidos por cliente", description = "Retorna uma lista com todos os pedidos de um cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos do cliente"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<List<PedidoDTO>> listarPorCliente(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long clienteId) {
        List<PedidoDTO> pedidos = pedidoService.listarPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", 
               description = "Atualiza o status de um pedido. Status possíveis: PENDENTE, CONFIRMADO, PREPARANDO, ENVIADO, ENTREGUE, CANCELADO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    public ResponseEntity<PedidoDTO> atualizarStatus(
            @Parameter(description = "ID do pedido", required = true)
            @PathVariable Long id,
            @Valid @RequestBody AtualizarStatusPedidoDTO dto) {
        PedidoDTO pedido = pedidoService.atualizarStatus(id, dto);
        return ResponseEntity.ok(pedido);
    }
}

