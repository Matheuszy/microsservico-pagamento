package codex.alurafood.pagamento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "pagamentos")
@Getter
@Setter
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    private String numero;

    @NotBlank
    @Size(max = 7)
    private String expiracao;

    @NotBlank
    @Max(3)
    @Min(3)
    private String codigo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status = Status.CRIADO;

    @NotNull
    private Long pedidoId;

    @NotNull
    private Long formaDePagamento;

    public Pagamento() {

    }


    public Pagamento(BigDecimal valor, String nome, String numero, String expiracao, String codigo, Status status,  Long pedidoId, Long formaDePagamento) {
        this.valor = valor;
        this.nome = nome;
        this.numero = numero;
        this.expiracao = expiracao;
        this.codigo = codigo;
        this.status = status;
        this.pedidoId = pedidoId;
        this.formaDePagamento = formaDePagamento;

    }

}
