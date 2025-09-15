package br.com.fiap.sprint.api_gestao_contas.service;

// Pacote: com.seupacote.seuprojeto.service

import br.com.fiap.sprint.api_gestao_contas.dto.TarefaRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.dto.TarefaResponseDTO;
import br.com.fiap.sprint.api_gestao_contas.model.Tarefa;
import br.com.fiap.sprint.api_gestao_contas.repository.TarefaRepository;
// Importe a exceção personalizada que criaremos a seguir
import br.com.fiap.sprint.api_gestao_contas.exception.RecursoJaExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public TarefaResponseDTO criar(TarefaRequestDTO requestDTO) {
        // 1. Regra de negócio: Verificar se já existe uma tarefa com o mesmo título
        if (tarefaRepository.findByTitulo(requestDTO.getTitulo()).isPresent()) {
            // Lança uma exceção personalizada que será tratada globalmente
            throw new RecursoJaExistenteException("Já existe uma tarefa com o título informado.");
        }

        // 2. Mapear DTO para Entidade
        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setTitulo(requestDTO.getTitulo());
        novaTarefa.setDescricao(requestDTO.getDescricao());
        novaTarefa.setConcluida(false); // Regra de negócio: sempre começa como não concluída

        // 3. Persistir no banco de dados
        Tarefa tarefaSalva = tarefaRepository.save(novaTarefa);

        // 4. Mapear Entidade para DTO de resposta
        TarefaResponseDTO responseDTO = new TarefaResponseDTO();
        responseDTO.setId(tarefaSalva.getId());
        responseDTO.setTitulo(tarefaSalva.getTitulo());
        responseDTO.setDescricao(tarefaSalva.getDescricao());
        responseDTO.setConcluida(tarefaSalva.isConcluida());

        return responseDTO;
    }
}