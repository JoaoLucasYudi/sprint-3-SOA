package br.com.fiap.sprint.api_gestao_contas.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario implements UserDetails {

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

    @CreationTimestamp
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusUsuario status;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transacao> transacoes;

    public enum StatusUsuario {
        ATIVO,
        BLOQUEADO,
        AUTO_EXCLUIDO
    }

    // --- MÉTODOS EXIGIDOS PELA INTERFACE UserDetails ---

    /**
     * Por enquanto, estamos definindo que todo usuário tem a permissão 'ROLE_USER'.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Retorna a senha usada para autenticar o usuário.
     * que o usuário enviou no login (usando o BCrypt).
     */
    @Override
    public String getPassword() {
        return this.senha;
    }

    /**
     * Retorna o nome de usuário usado para autenticar o usuário.
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    /**
     * Indica se a conta do usuário expirou.
     * Retornando 'true' significa que a conta nunca expira.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica se o usuário está bloqueado ou não.
     * Aqui, integramos com o nosso enum 'StatusUsuario'. Se o status for
     * BLOQUEADO, a conta estará travada.
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.status != StatusUsuario.BLOQUEADO;
    }

    /**
     * Indica se as credenciais do usuário (senha) expiraram.
     * Retornando 'true' significa que a senha nunca expira.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica se o usuário está habilitado ou desabilitado.
     * Um usuário só está ativo para o sistema se seu status for 'ATIVO'.
     */
    @Override
    public boolean isEnabled() {
        return this.status == StatusUsuario.ATIVO;
    }
}