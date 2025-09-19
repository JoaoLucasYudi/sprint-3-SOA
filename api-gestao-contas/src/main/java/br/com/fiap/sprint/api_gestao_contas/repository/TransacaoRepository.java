package br.com.fiap.sprint.api_gestao_contas.repository;

import br.com.fiap.sprint.api_gestao_contas.model.Transacao;
import br.com.fiap.sprint.api_gestao_contas.model.Transacao.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    // O Spring Data JPA cria a consulta automaticamente a partir do nome do método!
    List<Transacao> findByUsuarioIdAndTipoTransacaoAndDataTransacaoBetween(
            Long usuarioId,
            TipoTransacao tipo,
            LocalDateTime inicioMes,
            LocalDateTime fimMes
    );
}