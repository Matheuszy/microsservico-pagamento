package codex.alurafood.pagamento.dto.response;

import codex.alurafood.pagamento.model.Status;
import codex.alurafood.pagamento.model.TipoPagamento;
import java.math.BigDecimal;

public record PagamentoResponse(
        Long id,
        BigDecimal valor,
        Status status,
        TipoPagamento formaDePagamento
) {
}