package br.com.fiap.sprint.api_gestao_contas.controller;

import br.com.fiap.sprint.api_gestao_contas.dto.DepositoRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.dto.TransacaoResponseDTO;
import br.com.fiap.sprint.api_gestao_contas.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes") // <-- A URL base agora é /transacoes
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/deposito") // <-- A URL completa será /transacoes/deposito
    public ResponseEntity<TransacaoResponseDTO> depositar(@Valid @RequestBody DepositoRequestDTO requestDTO) {
        TransacaoResponseDTO responseDTO = transacaoService.depositar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}