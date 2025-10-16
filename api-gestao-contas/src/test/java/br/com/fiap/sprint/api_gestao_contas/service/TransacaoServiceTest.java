package br.com.fiap.sprint.api_gestao_contas.service;

import br.com.fiap.sprint.api_gestao_contas.dto.DepositoRequestDTO;
import br.com.fiap.sprint.api_gestao_contas.exception.ValidacaoNegocioException;
import br.com.fiap.sprint.api_gestao_contas.model.Transacao;
import br.com.fiap.sprint.api_gestao_contas.model.Usuario;
import br.com.fiap.sprint.api_gestao_contas.repository.TransacaoRepository;
import br.com.fiap.sprint.api_gestao_contas.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Habilita a integração do Mockito com o JUnit 5.
@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    // @Mock cria uma versão "falsa" (um mock) do repositório.
    // Ele não acessará o banco de dados; nós diremos a ele como se comportar.
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    // @InjectMocks cria uma instância real do TransacaoService,
    // mas injeta os MOCKS (@Mock) criados acima em suas dependências.
    @InjectMocks
    private TransacaoService transacaoService;

    @Test
    void depositar_DeveLancarExcecao_QuandoLimiteMensalForExcedido() {
        // --- ARRANGE (Preparação do Cenário) ---

        // 1. Crie os dados de entrada para o teste.
        var requestDTO = new DepositoRequestDTO();
        requestDTO.setUsuarioId(1L);
        requestDTO.setValor(new BigDecimal("100.01")); // O valor que vai estourar o limite

        // 2. Crie um usuário falso para ser retornado pelo mock do repositório.
        var usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setLimiteDepositoMensal(new BigDecimal("500.00"));

        // 3. Crie uma transação anterior falsa.
        var transacaoAnteriorMock = new Transacao();
        transacaoAnteriorMock.setValor(new BigDecimal("400.00"));

        // 4. Programe o comportamento dos mocks:
        // "QUANDO o método findById(1L) do usuarioRepository for chamado, ENTÃO retorne nosso usuário falso."
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));

        // "QUANDO o método find... do transacaoRepository for chamado (com quaisquer argumentos),
        // ENTÃO retorne uma lista contendo nossa transação falsa."
        when(transacaoRepository.findByUsuarioIdAndTipoTransacaoAndDataTransacaoBetween(
                any(Long.class), any(Transacao.TipoTransacao.class), any(LocalDateTime.class), any(LocalDateTime.class))
        ).thenReturn(List.of(transacaoAnteriorMock));


        // --- ACT & ASSERT (Ação e Verificação) ---

        // Verifique se uma exceção do tipo ValidacaoNegocioException é lançada
        // QUANDO o método depositar é chamado com os dados que preparamos.
        assertThrows(ValidacaoNegocioException.class, () -> {
            transacaoService.depositar(requestDTO);
        });
    }
}