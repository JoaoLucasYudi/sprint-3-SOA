package br.com.fiap.sprint.api_gestao_contas.service;

import br.com.fiap.sprint.api_gestao_contas.dto.UsuarioRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.dto.UsuarioResponseDTO;

public interface UsuarioServiceInterface {

    /**
     * Assinatura do método para cadastrar um novo usuário.
     *
     * @param requestDTO O objeto de transferência de dados (DTO) contendo as informações do novo usuário.
     * @return Um DTO com os dados do usuário que foi salvo no banco de dados.
     */
    UsuarioResponseDTO cadastrar(UsuarioRequestDTO requestDTO);

}