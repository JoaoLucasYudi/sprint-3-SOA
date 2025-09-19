package br.com.fiap.sprint.api_gestao_contas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(name = "limite_deposito_mensal", nullable = false)
    private BigDecimal limiteDepositoMensal;

    @CreationTimestamp // O Hibernate vai gerenciar a data de criação automaticamente
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING) // Grava o nome do Enum ("ATIVO") no banco, em vez de um número
    @Column(nullable = false)
    private StatusUsuario status;

    // Relacionamento: Um usuário pode ter muitas transações
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transacao> transacoes;

    public enum StatusUsuario {
        ATIVO,
        BLOQUEADO,
        AUTO_EXCLUIDO
    }
}