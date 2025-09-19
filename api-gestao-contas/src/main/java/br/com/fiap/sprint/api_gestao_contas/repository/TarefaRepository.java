package br.com.fiap.sprint.api_gestao_contas.repository;

import br.com.fiap.sprint.api_gestao_contas.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    // Método customizado para buscar uma tarefa pelo título
    Optional<Tarefa> findByTitulo(String titulo);
}