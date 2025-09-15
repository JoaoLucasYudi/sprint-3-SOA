package br.com.fiap.sprint.api_gestao_contas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TarefaRequestDTO {

    @NotBlank(message = "O título não pode ser vazio.")
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres.")
    private String titulo;

    private String descricao;

    // Note que não temos 'id' nem 'concluida' aqui.
    // O ID é gerado pelo banco e uma nova tarefa sempre começa como "não concluída".
}