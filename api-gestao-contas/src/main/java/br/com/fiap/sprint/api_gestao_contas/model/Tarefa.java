package br.com.fiap.sprint.api_gestao_contas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tarefas")
@Data
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    @Column
    private String descricao;

    @Column(nullable = false)
    private boolean concluida;
}