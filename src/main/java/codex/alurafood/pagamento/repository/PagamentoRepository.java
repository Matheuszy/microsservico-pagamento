package codex.alurafood.pagamento.repository;

import codex.alurafood.pagamento.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
