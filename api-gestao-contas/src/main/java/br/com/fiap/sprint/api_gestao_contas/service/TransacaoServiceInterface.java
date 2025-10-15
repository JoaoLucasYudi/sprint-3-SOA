package br.com.fiap.sprint.api_gestao_contas.service;

import br.com.fiap.sprint.api_gestao_contas.dto.DepositoRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.dto.TransacaoResponseDTO;

public interface TransacaoServiceInterface {

    /**
     * Assinatura do método para realizar um depósito na conta de um usuário.
     *
     * @param requestDTO O objeto de transferência de dados (DTO) com o ID do usuário e o valor do depósito.
     * @return Um DTO com os detalhes da transação de depósito que foi criada.
     */
    TransacaoResponseDTO depositar(DepositoRequestDTO requestDTO);

}