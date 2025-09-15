package br.com.fiap.sprint.api_gestao_contas.model;

import jakarta.persistence.*;
import lombok.Data; // Do Lombok, para gerar getters, setters, etc.

@Entity
@Table(name = "tarefas")
@Data // Gera getters, setters, toString, equals e hashCode
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

    // Construtor, getters e setters são gerados pelo Lombok
}