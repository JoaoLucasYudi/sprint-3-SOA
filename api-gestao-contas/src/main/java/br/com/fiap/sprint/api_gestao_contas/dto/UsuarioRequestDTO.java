package br.com.fiap.sprint.api_gestao_contas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Formato de email inválido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String senha;

    @NotNull(message = "O limite de depósito mensal é obrigatório.")
    @Positive(message = "O limite de depósito deve ser um valor positivo.")
    private BigDecimal limiteDepositoMensal;
}