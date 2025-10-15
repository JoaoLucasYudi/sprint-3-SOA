package br.com.fiap.sprint.api_gestao_contas.controller;

import br.com.fiap.sprint.api_gestao_contas.dto.DepositoRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.dto.TransacaoResponseDTO;
// 1. IMPORT DA MUDANÇA: Importamos a INTERFACE em vez da classe concreta.
import br.com.fiap.sprint.api_gestao_contas.service.TransacaoServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    // 2. PONTO-CHAVE DA MUDANÇA: A injeção de dependência agora é feita pela INTERFACE.
    // O controller não sabe mais qual classe específica executa a lógica de depósito.
    // Ele apenas confia que qualquer classe que implemente TransacaoServiceInterface
    // terá um método 'depositar' funcional.
    private TransacaoServiceInterface transacaoService;

    @PostMapping("/deposito")
    public ResponseEntity<TransacaoResponseDTO> depositar(@Valid @RequestBody DepositoRequestDTO requestDTO) {
        // 3. NENHUMA MUDANÇA AQUI: O código dentro do método permanece idêntico.
        TransacaoResponseDTO responseDTO = transacaoService.depositar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}