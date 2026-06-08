package codex.alurafood.pagamento.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pagamentos_pix")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class PagamentoPix extends Pagamento {

    @Enumerated(EnumType.STRING)
    private TipoPagamento formaDePagamento = TipoPagamento.PIX;

    public PagamentoPix() {}
}
