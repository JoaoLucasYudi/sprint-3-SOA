package br.com.fiap.sprint.api_gestao_contas.repository;

import br.com.fiap.sprint.api_gestao_contas.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    // Podemos adicionar métodos de busca específicos aqui depois, se precisarmos
}