package com.challenge.OrderProcessingManagement.dto;

import com.challenge.OrderProcessingManagement.model.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa um pedido no sistema")
public class PedidoDTO {
    @Schema(description = "ID do pedido", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @NotNull(message = "Cliente é obrigatório")
    @Schema(description = "ID do cliente", example = "1", required = true)
    private Long clienteId;
    
    @Schema(description = "Nome do cliente", example = "João Silva", accessMode = Schema.AccessMode.READ_ONLY)
    private String clienteNome;
    
    @Schema(description = "Status do pedido", example = "PENDENTE", accessMode = Schema.AccessMode.READ_ONLY)
    private StatusPedido status;
    
    @Schema(description = "Valor total do pedido", example = "199.98", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal valorTotal;
    
    @NotEmpty(message = "Pedido deve conter ao menos um item")
    @Valid
    @Schema(description = "Lista de itens do pedido", required = true)
    private List<ItemPedidoDTO> itens;
    
    @Schema(description = "Data de criação do pedido", example = "2024-01-15T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataCriacao;
    
    @Schema(description = "Data da última atualização do pedido", example = "2024-01-15T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataAtualizacao;
}

