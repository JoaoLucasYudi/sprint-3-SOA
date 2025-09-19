package br.com.fiap.sprint.api_gestao_contas.service;

import br.com.fiap.sprint.api_gestao_contas.dto.UsuarioRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.dto.UsuarioResponseDTO;
import br.com.fiap.sprint.api_gestao_contas.exception.RecursoJaExistenteException;
import br.com.fiap.sprint.api_gestao_contas.model.Usuario;
import br.com.fiap.sprint.api_gestao_contas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO requestDTO) {
        // 1. Regra de negócio: Verificar se o email já está em uso
        if (usuarioRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new RecursoJaExistenteException("O email informado já está em uso.");
        }

        // 2. Mapear DTO para Entidade
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(requestDTO.getNome());
        novoUsuario.setEmail(requestDTO.getEmail());
        novoUsuario.setSenha(requestDTO.getSenha()); // ATENÇÃO: Em um projeto real, a senha deve ser criptografada!
        novoUsuario.setLimiteDepositoMensal(requestDTO.getLimiteDepositoMensal());
        novoUsuario.setStatus(Usuario.StatusUsuario.ATIVO); // Define o status inicial

        // 3. Persistir no banco de dados
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        // 4. Mapear Entidade para DTO de resposta
        return mapToResponseDTO(usuarioSalvo);
    }

    private UsuarioResponseDTO mapToResponseDTO(Usuario usuario) {
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(usuario.getId());
        responseDTO.setNome(usuario.getNome());
        responseDTO.setEmail(usuario.getEmail());
        responseDTO.setLimiteDepositoMensal(usuario.getLimiteDepositoMensal());
        responseDTO.setDataCriacao(usuario.getDataCriacao());
        return responseDTO;
    }
}