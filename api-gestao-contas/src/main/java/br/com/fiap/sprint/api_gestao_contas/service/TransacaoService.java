package br.com.fiap.sprint.api_gestao_contas.service;

import br.com.fiap.sprint.api_gestao_contas.dto.DepositoRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.dto.TransacaoResponseDTO;
import br.com.fiap.sprint.api_gestao_contas.exception.RecursoNaoEncontradoException;
import br.com.fiap.sprint.api_gestao_contas.exception.ValidacaoNegocioException;
import br.com.fiap.sprint.api_gestao_contas.model.Transacao;
import br.com.fiap.sprint.api_gestao_contas.model.Usuario;
import br.com.fiap.sprint.api_gestao_contas.repository.TransacaoRepository;
import br.com.fiap.sprint.api_gestao_contas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

/**
 * Implementação concreta das regras de negócio para Transações.
 * A anotação @Service indica ao Spring que esta classe contém a lógica de negócio
 * e deve ser gerenciada como um Bean.
 *
 * PONTO PRINCIPAL DA MUDANÇA:
 * Ao adicionar "implements TransacaoServiceInterface", esta classe se compromete
 * a seguir o contrato definido pela interface, tornando o sistema mais flexível e desacoplado.
 */
@Service
public class TransacaoService implements TransacaoServiceInterface {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * A anotação @Override confirma que este método está implementando
     * uma assinatura definida na interface TransacaoServiceInterface.
     * Toda a lógica para validar o limite de depósito está contida aqui.
     */
    @Override
    public TransacaoResponseDTO depositar(DepositoRequestDTO requestDTO) {
        // 1. Busca o usuário (LÓGICA INALTERADA)
        Usuario usuario = usuarioRepository.findById(requestDTO.getUsuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + requestDTO.getUsuarioId()));

        // 2. Define o período do mês atual (LÓGICA INALTERADA)
        LocalDateTime inicioMes = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime fimMes = YearMonth.now().atEndOfMonth().atTime(23, 59, 59);

        // 3. Busca a lista de depósitos do mês (LÓGICA INALTERADA)
        List<Transacao> depositosDoMes = transacaoRepository.findByUsuarioIdAndTipoTransacaoAndDataTransacaoBetween(
                usuario.getId(),
                Transacao.TipoTransacao.DEPOSITO,
                inicioMes,
                fimMes
        );

        // 4. Soma os valores dos depósitos na lista (LÓGICA INALTERADA)
        BigDecimal totalDepositadoNoMes = depositosDoMes.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 5. Valida a regra de negócio do limite (LÓGICA INALTERADA)
        if (totalDepositadoNoMes.add(requestDTO.getValor()).compareTo(usuario.getLimiteDepositoMensal()) > 0) {
            throw new ValidacaoNegocioException("Limite de depósito mensal excedido. Limite: " + usuario.getLimiteDepositoMensal());
        }

        // 6. Se tudo estiver OK, cria e salva a nova transação (LÓGICA INALTERADA)
        Transacao novaTransacao = new Transacao();
        novaTransacao.setUsuario(usuario);
        novaTransacao.setValor(requestDTO.getValor());
        novaTransacao.setTipoTransacao(Transacao.TipoTransacao.DEPOSITO);

        Transacao transacaoSalva = transacaoRepository.save(novaTransacao);

        // 7. Retorna o DTO de resposta (LÓGICA INALTERADA)
        return mapToResponseDTO(transacaoSalva);
    }

    /**
     * Método auxiliar privado para mapear a entidade Transacao para o DTO de resposta.
     * Não faz parte do contrato da interface e sua lógica permanece a mesma.
     * (LÓGICA INALTERADA)
     */
    private TransacaoResponseDTO mapToResponseDTO(Transacao transacao) {
        return new TransacaoResponseDTO(
                transacao.getId(),
                transacao.getUsuario().getId(),
                transacao.getTipoTransacao(),
                transacao.getValor(),
                transacao.getDataTransacao()
        );
    }
}