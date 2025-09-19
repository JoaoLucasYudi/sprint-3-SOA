package br.com.fiap.sprint.api_gestao_contas.dto;

import br.com.fiap.sprint.api_gestao_contas.model.Transacao;
import lombok.AllArgsConstructor; // <-- Importe esta
import lombok.Data;
import lombok.NoArgsConstructor; // <-- Importe esta
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor // <-- Adicione esta linha
@AllArgsConstructor // <-- Adicione esta linha
public class TransacaoResponseDTO {
    private Long id;
    private Long usuarioId;
    private Transacao.TipoTransacao tipoTransacao;
    private BigDecimal valor;
    private LocalDateTime dataTransacao;
}