package com.challenge.OrderProcessingManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Representa um cliente no sistema")
public class ClienteDTO {
    @Schema(description = "ID do cliente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Schema(description = "Nome completo do cliente", example = "João Silva", required = true)
    private String nome;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Schema(description = "Email do cliente (deve ser único)", example = "joao@email.com", required = true)
    private String email;
    
    @NotBlank(message = "CPF é obrigatório")
    @Schema(description = "CPF do cliente (deve ser único)", example = "123.456.789-00", required = true)
    private String cpf;
    
    @Schema(description = "Data de criação do cliente", example = "2024-01-15T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataCriacao;
}

