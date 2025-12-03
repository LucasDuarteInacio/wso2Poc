package com.challenge.OrderProcessingManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa um produto no sistema")
public class ProdutoDTO {
    @Schema(description = "ID do produto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome do produto", example = "Notebook Dell", required = true)
    private String nome;
    
    @Schema(description = "Descrição detalhada do produto", example = "Notebook Dell Inspiron 15, 8GB RAM, 256GB SSD")
    private String descricao;
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    @Schema(description = "Preço do produto", example = "2999.99", required = true)
    private BigDecimal preco;
    
    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Schema(description = "Quantidade disponível em estoque", example = "50", required = true)
    private Integer quantidadeEstoque;
    
    @Schema(description = "Data de criação do produto", example = "2024-01-15T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataCriacao;
    
    @Schema(description = "Data da última atualização do produto", example = "2024-01-15T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataAtualizacao;
}

