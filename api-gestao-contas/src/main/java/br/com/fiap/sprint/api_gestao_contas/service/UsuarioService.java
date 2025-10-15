package br.com.fiap.sprint.api_gestao_contas.service;

import br.com.fiap.sprint.api_gestao_contas.dto.UsuarioRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.dto.UsuarioResponseDTO;
import br.com.fiap.sprint.api_gestao_contas.exception.RecursoJaExistenteException;
import br.com.fiap.sprint.api_gestao_contas.model.Usuario;
import br.com.fiap.sprint.api_gestao_contas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementação concreta das regras de negócio para usuários.
 * A anotação @Service informa ao Spring que esta é uma classe de serviço gerenciada por ele.
 *
 * PONTO PRINCIPAL DA MUDANÇA:
 * A cláusula "implements UsuarioServiceInterface" garante que esta classe
 * está cumprindo o contrato definido na interface. Se um novo método for adicionado
 * na interface, esta classe será obrigada a implementá-lo.
 */
@Service
public class UsuarioService implements UsuarioServiceInterface {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * A anotação @Override é uma boa prática. Ela informa ao compilador e a outros
     * desenvolvedores que este método está, intencionalmente, sobrescrevendo um
     * método definido na interface (o "contrato"). Isso ajuda a evitar erros de digitação
     * no nome do método ou nos parâmetros.
     */
    @Override
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO requestDTO) {
        // 1. Regra de negócio: Verificar se o email já está em uso (LÓGICA INALTERADA)
        if (usuarioRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new RecursoJaExistenteException("O email informado já está em uso.");
        }

        // 2. Mapear DTO para Entidade (LÓGICA INALTERADA)
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(requestDTO.getNome());
        novoUsuario.setEmail(requestDTO.getEmail());
        novoUsuario.setSenha(requestDTO.getSenha()); // ATENÇÃO: Iremos mudar isso no próximo passo de segurança!
        novoUsuario.setLimiteDepositoMensal(requestDTO.getLimiteDepositoMensal());
        novoUsuario.setStatus(Usuario.StatusUsuario.ATIVO); // Define o status inicial

        // 3. Persistir no banco de dados (LÓGICA INALTERADA)
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        // 4. Mapear Entidade para DTO de resposta (LÓGICA INALTERADA)
        return mapToResponseDTO(usuarioSalvo);
    }

    /**
     * Este é um método privado, auxiliar, que não faz parte do contrato público
     * da interface. Ele continua existindo apenas dentro desta classe.
     * (LÓGICA INALTERADA)
     */
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