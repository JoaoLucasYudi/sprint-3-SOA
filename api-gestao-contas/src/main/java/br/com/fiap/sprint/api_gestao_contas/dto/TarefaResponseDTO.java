package br.com.fiap.sprint.api_gestao_contas.dto;

import lombok.Data;

@Data
public class TarefaResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private boolean concluida;
}