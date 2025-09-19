package br.com.fiap.sprint.api_gestao_contas.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private BigDecimal limiteDepositoMensal;
    private LocalDateTime dataCriacao;
}