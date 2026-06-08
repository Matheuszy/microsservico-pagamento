package codex.alurafood.pagamento.dto.request;


import codex.alurafood.pagamento.model.Status;

import java.math.BigDecimal;

public record PagamentoRequest(
    BigDecimal valor,
    String nome,
    String numero,
    String expiracao,
    Status status,
    String pedidoId,
    String formaDePagamento
) {
}
