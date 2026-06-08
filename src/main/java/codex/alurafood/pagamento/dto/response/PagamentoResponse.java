package codex.alurafood.pagamento.dto.response;

import codex.alurafood.pagamento.model.Status;

import java.math.BigDecimal;

public record PagamentoResponse(
        Long id,
        BigDecimal valor,
        Status status
) {
}
