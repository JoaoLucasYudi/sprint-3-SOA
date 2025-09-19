package br.com.fiap.sprint.api_gestao_contas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class DepositoRequestDTO {
    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    @NotNull(message = "O valor é obrigatório.")
    @Positive(message = "O valor do depósito deve ser positivo.")
    private BigDecimal valor;
}