package codex.alurafood.pagamento.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pagamentos_cartao")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class PagamentoCartao extends Pagamento {

    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;

    @Enumerated(EnumType.STRING)
    private TipoPagamento formaDePagamento;

    public PagamentoCartao() {}
}