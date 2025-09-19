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

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public TransacaoResponseDTO depositar(DepositoRequestDTO requestDTO) {
        // 1. Busca o usuário (isso não interfere, apenas lê os dados dele)
        Usuario usuario = usuarioRepository.findById(requestDTO.getUsuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + requestDTO.getUsuarioId()));

        // 2. Define o período do mês atual
        LocalDateTime inicioMes = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime fimMes = YearMonth.now().atEndOfMonth().atTime(23, 59, 59);

        // 3. Busca a lista de depósitos do mês
        List<Transacao> depositosDoMes = transacaoRepository.findByUsuarioIdAndTipoTransacaoAndDataTransacaoBetween(
                usuario.getId(),
                Transacao.TipoTransacao.DEPOSITO,
                inicioMes,
                fimMes
        );

        // 4. Soma os valores dos depósitos na lista
        BigDecimal totalDepositadoNoMes = depositosDoMes.stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 5. Valida a regra de negócio do limite
        if (totalDepositadoNoMes.add(requestDTO.getValor()).compareTo(usuario.getLimiteDepositoMensal()) > 0) {
            throw new ValidacaoNegocioException("Limite de depósito mensal excedido. Limite: " + usuario.getLimiteDepositoMensal());
        }

        // 6. Se tudo estiver OK, cria e salva a nova transação
        Transacao novaTransacao = new Transacao();
        novaTransacao.setUsuario(usuario);
        novaTransacao.setValor(requestDTO.getValor());
        novaTransacao.setTipoTransacao(Transacao.TipoTransacao.DEPOSITO);

        Transacao transacaoSalva = transacaoRepository.save(novaTransacao);

        // 7. Retorna o DTO de resposta
        return mapToResponseDTO(transacaoSalva);
    }

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