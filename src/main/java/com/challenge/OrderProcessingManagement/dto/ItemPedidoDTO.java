package com.challenge.OrderProcessingManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa um item de pedido")
public class ItemPedidoDTO {
    @Schema(description = "ID do item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @NotNull(message = "Produto é obrigatório")
    @Schema(description = "ID do produto", example = "1", required = true)
    private Long produtoId;
    
    @Schema(description = "Nome do produto", example = "Notebook Dell", accessMode = Schema.AccessMode.READ_ONLY)
    private String produtoNome;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    @Schema(description = "Quantidade do produto no pedido", example = "2", required = true)
    private Integer quantidade;
    
    @Schema(description = "Preço unitário do produto no momento da compra", example = "99.99", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal precoUnitario;
    
    @Schema(description = "Subtotal do item (quantidade × preço unitário)", example = "199.98", accessMode = Schema.AccessMode.READ_ONLY)
    private BigDecimal subtotal;
}

