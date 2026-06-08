package codex.alurafood.pagamento.dto.request;

import codex.alurafood.pagamento.model.TipoPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record PagamentoCartaoRequest(
        @NotNull @Positive BigDecimal valor,
        @NotNull Long pedidoId,
        @NotNull TipoPagamento formaDePagamento,

        @NotBlank @Size(max = 100) String nome,
        @NotBlank String numero,
        @NotBlank @Size(max = 10) String expiracao,
        @NotBlank @Size(min = 3, max = 3) String codigo
) implements PagamentoRequest {}