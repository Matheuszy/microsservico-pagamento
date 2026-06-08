package codex.alurafood.pagamento.dto.request;

import codex.alurafood.pagamento.model.TipoPagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PagamentoPixRequest(
        @NotNull @Positive BigDecimal valor,
        @NotNull Long pedidoId,
        @NotNull TipoPagamento formaDePagamento
) implements PagamentoRequest {}
