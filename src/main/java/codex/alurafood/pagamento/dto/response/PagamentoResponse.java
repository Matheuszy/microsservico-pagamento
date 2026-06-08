package codex.alurafood.pagamento.dto.response;

import codex.alurafood.pagamento.model.Status;

import java.math.BigDecimal;

public record PagamentoResponse(
        BigDecimal valor,
        Status status
) {
}
