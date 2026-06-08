package codex.alurafood.pagamento.service;

import codex.alurafood.pagamento.dto.request.PagamentoRequest;
import codex.alurafood.pagamento.dto.response.PagamentoResponse;
import codex.alurafood.pagamento.model.Pagamento;
import codex.alurafood.pagamento.repository.PagamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagamentoService {
    private final PagamentoRepository pagamentoRepository;
    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Page<PagamentoResponse> listarPagamentos(Pageable paginacao) {
        return pagamentoRepository.findAll(paginacao)
                .map( p -> new  PagamentoResponse(p.getValor(), p.getStatus()));
    }

    public PagamentoResponse buscarPagamentoPorId(Long id) {
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);
        return pagamento.map(p -> new PagamentoResponse(p.getValor(), p.getStatus()))
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }

    public PagamentoResponse criarPagamento(PagamentoRequest pagamentoRequest) {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(pagamentoRequest.valor());
        pagamento.setNome(pagamentoRequest.nome());
        pagamento.setNumero(pagamentoRequest.numero());
        pagamento.setExpiracao(pagamentoRequest.expiracao());
        pagamento.setStatus(pagamentoRequest.status());
        pagamento.setPedidoId(pagamentoRequest.pedidoId());
        pagamento.setFormaDePagamento(pagamentoRequest.formaDePagamento());

        pagamentoRepository.save(pagamento);
        return ResponseEntity.status(201)
                .body(new PagamentoResponse(pagamento.getValor(),
                        pagamento.getStatus()))
                .getBody();

    }
}
