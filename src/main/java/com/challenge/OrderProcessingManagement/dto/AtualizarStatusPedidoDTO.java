package com.challenge.OrderProcessingManagement.dto;

import com.challenge.OrderProcessingManagement.model.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para atualização de status do pedido")
public class AtualizarStatusPedidoDTO {
    @NotNull(message = "Status é obrigatório")
    @Schema(description = "Novo status do pedido", 
            example = "CONFIRMADO", 
            allowableValues = {"PENDENTE", "CONFIRMADO", "PREPARANDO", "ENVIADO", "ENTREGUE", "CANCELADO"},
            required = true)
    private StatusPedido status;
}

