package br.com.fiap.sprint.api_gestao_contas.controller;

// Pacote: com.seupacote.seuprojeto.controller

import br.com.fiap.sprint.api_gestao_contas.dto.TarefaRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.dto.TarefaResponseDTO;
import br.com.fiap.sprint.api_gestao_contas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> criar(@Valid @RequestBody TarefaRequestDTO requestDTO) {
        TarefaResponseDTO responseDTO = tarefaService.criar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}