package codex.alurafood.pagamento.dto.request;


import codex.alurafood.pagamento.model.Status;
import codex.alurafood.pagamento.model.TipoPagamento;

import java.math.BigDecimal;

public record PagamentoRequest(
    BigDecimal valor,
    String nome,
    String numero,
    String expiracao,
    String codigo,
    Long pedidoId,
    TipoPagamento formaDePagamento
) {
}
