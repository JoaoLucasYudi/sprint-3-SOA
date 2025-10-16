package br.com.fiap.sprint.api_gestao_contas.service;

import br.com.fiap.sprint.api_gestao_contas.dto.UsuarioRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.dto.UsuarioResponseDTO;
import br.com.fiap.sprint.api_gestao_contas.exception.RecursoJaExistenteException;
import br.com.fiap.sprint.api_gestao_contas.model.Usuario;
import br.com.fiap.sprint.api_gestao_contas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UsuarioServiceInterface {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO requestDTO) {
        if (usuarioRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new RecursoJaExistenteException("O email informado já está em uso.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(requestDTO.getNome());
        novoUsuario.setEmail(requestDTO.getEmail());

        // Este método aplica o algoritmo BCrypt para gerar um hash seguro da senha.
        // É este hash que será salvo no banco de dados.
        novoUsuario.setSenha(passwordEncoder.encode(requestDTO.getSenha()));

        novoUsuario.setLimiteDepositoMensal(requestDTO.getLimiteDepositoMensal());
        novoUsuario.setStatus(Usuario.StatusUsuario.ATIVO);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

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