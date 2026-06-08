package codex.alurafood.pagamento.dto.request;

import codex.alurafood.pagamento.model.TipoPagamento;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.math.BigDecimal;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "formaDePagamento",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PagamentoPixRequest.class, name = "PIX"),
        @JsonSubTypes.Type(value = PagamentoCartaoRequest.class, name = "CREDITO"),
        @JsonSubTypes.Type(value = PagamentoCartaoRequest.class, name = "DEBITO")
})
public interface PagamentoRequest {
    BigDecimal valor();
    Long pedidoId();
    TipoPagamento formaDePagamento();
}